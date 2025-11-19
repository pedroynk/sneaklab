package br.com.uniceplac.sneaklab.application.service;

import br.com.uniceplac.sneaklab.application.ports.out.NotifierPort;
import br.com.uniceplac.sneaklab.domain.Pagamento;
import br.com.uniceplac.sneaklab.domain.Pedido;
import br.com.uniceplac.sneaklab.domain.StatusPagamento;
import br.com.uniceplac.sneaklab.domain.StatusPedido;
import org.springframework.stereotype.Service;

/**
 Serviço de aplicação responsável por traduzir eventos de negócio
 (pedido criado, pago, cancelado, pagamento aprovado/estornado)
 em mensagens de notificação de alto nível.
*/
@Service
public class NotificationService {

    private final NotifierPort notifierPort;

    public NotificationService(NotifierPort notifierPort) {
        this.notifierPort = notifierPort;
    }

    public void notificarStatusPedido(Pedido pedido, String destinoContato) {
        if (destinoContato == null || destinoContato.isBlank()) {
            return;
        }

        String assunto = switch (pedido.getStatus()) {
            case RASCUNHO   -> "[Sneaklab] Seu pedido foi criado (rascunho)";
            case PAGO       -> "[Sneaklab] Seu pedido foi pago!";
            case ENVIADO    -> "[Sneaklab] Seu pedido foi enviado!";
            case ENTREGUE   -> "[Sneaklab] Seu pedido foi entregue 🙌";
            case CANCELADO  -> "[Sneaklab] Seu pedido foi cancelado";
        };

        String mensagem = """
                Olá!
                
                O status do seu pedido #%d foi atualizado para: %s.
                Valor total: R$ %.2f
                
                Acompanhe seus pedidos pelo app Sneaklab.
                
                Obrigado por comprar com a gente! 👟
                """.formatted(
                pedido.getId(),
                pedido.getStatus(),
                pedido.getTotal()
        );

        notifierPort.enviarNotificacao(destinoContato, assunto, mensagem);
    }

    //Notifica o cliente sobre aprovação de pagamento.
    public void notificarPagamentoAprovado(Pagamento pagamento, String destinoContato) {
        if (destinoContato == null || destinoContato.isBlank()) {
            return;
        }

        if (pagamento.getStatus() != StatusPagamento.APROVADO) {
            // Apenas dispara se de fato estiver APROVADO
            return;
        }

        String assunto = "[Sneaklab] Pagamento aprovado com sucesso 🎉";

        String mensagem = """
                Olá!
                
                Seu pagamento do pedido #%d foi APROVADO.
                Valor: R$ %.2f
                Forma de pagamento: %s
                
                Em breve seu pedido será processado e enviado.
                
                Equipe Sneaklab.
                """.formatted(
                pagamento.getIdPedido(),
                pagamento.getValor(),
                pagamento.getTipo()
        );

        notifierPort.enviarNotificacao(destinoContato, assunto, mensagem);
    }

    //Notifica o cliente sobre estorno de pagamento.
    public void notificarPagamentoEstornado(Pagamento pagamento, String destinoContato) {
        if (destinoContato == null || destinoContato.isBlank()) {
            return;
        }

        if (pagamento.getStatus() != StatusPagamento.ESTORNADO) {
            // Apenas dispara se de fato estiver ESTORNADO
            return;
        }

        String assunto = "[Sneaklab] Pagamento estornado";

        String mensagem = """
                Olá!
                
                Informamos que o pagamento do pedido #%d foi ESTORNADO.
                Valor: R$ %.2f
                Forma de pagamento: %s
                
                Caso você não reconheça esta operação, entre em contato com nosso suporte.
                
                Equipe Sneaklab.
                """.formatted(
                pagamento.getIdPedido(),
                pagamento.getValor(),
                pagamento.getTipo()
        );

        notifierPort.enviarNotificacao(destinoContato, assunto, mensagem);
    }

    //Exemplo genérico, para ser usado por outros serviços (Usuário, Produto, etc.).
    public void enviarNotificacaoGenerica(String destinoContato, String assunto, String mensagem) {
        if (destinoContato == null || destinoContato.isBlank()) {
            return;
        }
        notifierPort.enviarNotificacao(destinoContato, assunto, mensagem);
    }
}
