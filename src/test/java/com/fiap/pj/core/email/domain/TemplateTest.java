package com.fiap.pj.core.email.domain;

import com.fiap.pj.core.email.domain.enums.Template;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemplateTest {

    @Test
    void deveRetornarAssuntoPadraoBoasVindas() {
        assertEquals("Bem-vindo à nossa oficina!", Template.BOAS_VINDAS.getAssuntoPadrao());
    }

    @Test
    void deveRetornarAssuntoPadraoOrcamentoCriado() {
        assertEquals("Orçamento criado", Template.ORCAMENTO_CRIADO.getAssuntoPadrao());
    }

    @Test
    void deveRetornarAssuntoPadraoOrcamentoAprovado() {
        assertEquals("Orçamento aprovado", Template.ORCAMENTO_APROVADO.getAssuntoPadrao());
    }

    @Test
    void deveRetornarAssuntoPadraoOrcamentoRecusado() {
        assertEquals("Orçamento recusado", Template.ORCAMENTO_RECUSADO.getAssuntoPadrao());
    }

    @Test
    void deveRetornarAssuntoPadraoMudancaStatus() {
        assertEquals("Mudança de status da ordem de serviço", Template.ORDEM_SERVICO_MUDANCA_STATUS.getAssuntoPadrao());
    }
}