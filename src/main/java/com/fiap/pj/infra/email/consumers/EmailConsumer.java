package com.fiap.pj.infra.email.consumers;


import com.fiap.pj.core.email.app.usecase.EnviarEmailUseCase;
import com.fiap.pj.core.email.app.usecase.command.EnviarEmailCommand;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EnviarEmailUseCase enviarEmailUseCase;

    public EmailConsumer(EnviarEmailUseCase enviarEmailUseCase) {
        this.enviarEmailUseCase = enviarEmailUseCase;
    }

    @RabbitListener(queues = "${broker.queue.email}")
    public void receiveMessage(EmailMessage emailMessage) {
        enviarEmailUseCase.handle(
                new EnviarEmailCommand(
                        emailMessage.destinatario(),
                        emailMessage.template(),
                        emailMessage.args()
                )
        );
    }
}
