package com.fiap.pj.core.email.app;

import com.fiap.pj.core.email.app.gateways.EmailGateway;
import com.fiap.pj.core.email.app.usecase.command.EnviarEmailCommand;
import com.fiap.pj.core.email.domain.EmailTemplate;
import com.fiap.pj.core.email.domain.enums.Template;
import com.fiap.pj.core.email.exception.EmailTemplateExceptions.EmailTemplateNaoEncontradoException;
import com.fiap.pj.core.email.exception.EmailTemplateExceptions.EmailTemplateNaoFoiPossivelEnviarEmailException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EnviarEmailUseCaseImplTest {

    private EmailGateway emailGateway;
    private JavaMailSender mailSender;
    private EnviarEmailUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        emailGateway = mock(EmailGateway.class);
        mailSender = mock(JavaMailSender.class);
        useCase = new EnviarEmailUseCaseImpl(emailGateway, mailSender);
        setEmailEnabled(useCase, true);
    }

    @Test
    void deveEnviarEmailQuandoTemplateEncontrado() {
        EnviarEmailCommand cmd = new EnviarEmailCommand("dest@teste.com", Template.BOAS_VINDAS, List.of("nome"));
        EmailTemplate template = new EmailTemplate(UUID.randomUUID(), Template.BOAS_VINDAS, "Assunto", "<b>%s</b>", ZonedDateTime.now());

        when(emailGateway.buscarTemplate(Template.BOAS_VINDAS)).thenReturn(Optional.of(template));
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        useCase.handle(cmd);

        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    void deveBuscarTemplateDoResourceQuandoNaoEncontradoNoGateway() {
        EnviarEmailCommand cmd = new EnviarEmailCommand("dest@teste.com", Template.BOAS_VINDAS, null);
        when(emailGateway.buscarTemplate(Template.BOAS_VINDAS)).thenReturn(Optional.empty());
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        useCase.handle(cmd);

        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    void deveBuscarTemplateDoResourceOrcamentoAprovado() {
        EnviarEmailCommand cmd = new EnviarEmailCommand("dest@teste.com", Template.ORCAMENTO_APROVADO, null);
        when(emailGateway.buscarTemplate(Template.ORCAMENTO_APROVADO)).thenReturn(Optional.empty());
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        useCase.handle(cmd);

        verify(mailSender, times(1)).send(mimeMessage);
    }


    @Test
    void naoDeveEnviarEmailQuandoDesabilitado() {
        setEmailEnabled(useCase, false);
        EnviarEmailCommand cmd = new EnviarEmailCommand("dest@teste.com", Template.BOAS_VINDAS, null);

        useCase.handle(cmd);

        verify(mailSender, never()).send(any(MimeMessage.class));
    }

    @Test
    void deveLancarExcecaoQuandoFalhaAoEnviar() {
        EnviarEmailCommand cmd = new EnviarEmailCommand("dest@teste.com", Template.BOAS_VINDAS, null);
        EmailTemplate template = new EmailTemplate(UUID.randomUUID(), Template.BOAS_VINDAS, "Assunto", "corpo", ZonedDateTime.now());

        when(emailGateway.buscarTemplate(Template.BOAS_VINDAS)).thenReturn(Optional.of(template));
        when(mailSender.createMimeMessage()).thenThrow(new RuntimeException("Erro"));

        assertThrows(EmailTemplateNaoFoiPossivelEnviarEmailException.class, () -> useCase.handle(cmd));
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrarTemplateAoEnviar() {
        EnviarEmailCommand cmd = new EnviarEmailCommand("dest@teste.com", null, null);

        when(emailGateway.buscarTemplate(Template.BOAS_VINDAS)).thenReturn(Optional.empty());

        var thrown = catchThrowable(() -> useCase.handle(cmd));

        assertThat(thrown.getCause()).isInstanceOf(EmailTemplateNaoEncontradoException.class);


    }


    // Habilita envio de e-mail para os testes
    private void setEmailEnabled(EnviarEmailUseCaseImpl useCase, boolean enabled) {
        try {
            var field = EnviarEmailUseCaseImpl.class.getDeclaredField("emailEnabled");
            field.setAccessible(true);
            field.set(useCase, enabled);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}