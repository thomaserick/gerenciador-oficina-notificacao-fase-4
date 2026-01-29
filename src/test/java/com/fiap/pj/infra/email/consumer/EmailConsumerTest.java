package com.fiap.pj.infra.email.consumer;

import com.fiap.pj.core.email.app.usecase.EnviarEmailUseCase;
import com.fiap.pj.core.email.app.usecase.command.EnviarEmailCommand;
import com.fiap.pj.core.email.domain.enums.Template;
import com.fiap.pj.infra.email.consumers.EmailConsumer;
import com.fiap.pj.infra.email.consumers.EmailMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmailConsumerTest {

    private EnviarEmailUseCase enviarEmailUseCase;
    private EmailConsumer emailConsumer;

    @BeforeEach
    void setUp() {
        enviarEmailUseCase = mock(EnviarEmailUseCase.class);
        emailConsumer = new EmailConsumer(enviarEmailUseCase);
    }

    @Test
    void deveChamarHandleAoReceberMensagem() {
        EmailMessage emailMessage = mock(EmailMessage.class);
        when(emailMessage.destinatario()).thenReturn("teste@teste.com");
        when(emailMessage.template()).thenReturn(Template.BOAS_VINDAS);
        when(emailMessage.placeholders()).thenReturn(null);

        emailConsumer.receiveMessage(emailMessage);

        ArgumentCaptor<EnviarEmailCommand> captor = ArgumentCaptor.forClass(EnviarEmailCommand.class);
        verify(enviarEmailUseCase, times(1)).handle(captor.capture());

        EnviarEmailCommand command = captor.getValue();
        assert command.destinatario().equals("teste@teste.com");
        assert command.template().equals(Template.BOAS_VINDAS);
        assert command.placeholders() == null;
    }
}
