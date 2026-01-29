package com.fiap.pj.core.email.model;

import com.fiap.pj.core.email.domain.EmailTemplate;
import com.fiap.pj.core.email.domain.enums.Template;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailTemplateTest {

    @Test
    void deveCriarEmailTemplateComValoresCorretos() {
        UUID id = UUID.randomUUID();
        Template template = Template.BOAS_VINDAS;
        String assunto = "Assunto";
        String corpo = "<b>Nome</b>";
        ZonedDateTime dataCriacao = ZonedDateTime.now();

        EmailTemplate emailTemplate = new EmailTemplate(id, template, assunto, corpo, dataCriacao);

        assertEquals(id, emailTemplate.getId());
        assertEquals(template, emailTemplate.getTemplate());
        assertEquals(assunto, emailTemplate.getAssunto());
        assertEquals(corpo, emailTemplate.getCorpo());
        assertEquals(dataCriacao, emailTemplate.getDataCriacao());
    }

}
