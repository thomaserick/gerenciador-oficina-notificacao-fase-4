package com.fiap.pj.core.email.exception;

public class EmailTemplateExceptions {

    private EmailTemplateExceptions() {
    }

    public static class EmailTemplateNaoEncontradoException extends RuntimeException {
        public EmailTemplateNaoEncontradoException() {
            super("Template de e-mail não encontrado.");
        }
    }

    public static class EmailTemplateNaoFoiPossivelEnviarEmailException extends RuntimeException {

        public EmailTemplateNaoFoiPossivelEnviarEmailException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}