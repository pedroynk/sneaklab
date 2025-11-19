package br.com.uniceplac.sneaklab.application.ports.out;

public interface NotifierPort {
    void enviarNotificacao(String destino, String assunto, String mensagem);
}
