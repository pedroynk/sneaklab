package br.com.uniceplac.sneaklab.adapters.notifications;

import br.com.uniceplac.sneaklab.application.ports.out.NotifierPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


 /*
 Esse adapter simula o envio para um gateway externo,
 mas não precisa de nenhuma configuração extra (e funciona bem com H2).
 */
@Component
public class LoggingNotifierAdapter implements NotifierPort {

    private static final Logger logger = LoggerFactory.getLogger(LoggingNotifierAdapter.class);

    @Override
    public void enviarNotificacao(String destino, String assunto, String mensagem) {
        logger.info("""
                [NOTIFICAÇÃO ENVIADA]
                Destino: {}
                Assunto: {}
                Mensagem:
                {}
                """, destino, assunto, mensagem);
    }
}

