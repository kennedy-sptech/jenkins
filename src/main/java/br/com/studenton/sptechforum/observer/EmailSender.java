package br.com.studenton.sptechforum.observer;

import br.com.studenton.sptechforum.domain.PublicacaoEntity;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender extends Thread {

    private String email;
    private Integer status;
    private PublicacaoEntity publicacao;

    public EmailSender(String email, Integer status, PublicacaoEntity publicacao) {
        this.email = email;
        this.status = status;
        this.publicacao = publicacao;
    }

    @Override
    public void run() {

        Properties props = new Properties();
        /** Parâmetros de conexão com servidor Gmail */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("saopaulotechschool@gmail.com",
                                "#Gf01211078");
                    }
                });

        /** Ativa Debug para sessão */
        session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("saopaulotechschool@gmail.com"));
            //Remetente

            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse(email);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Atualização de status da sua "
                    .concat(publicacao.getTipoPublicacao() == 1 ? "publicação" : "pergunta"));//Assunto
            if (status == 1) {
                message.setText(String.format("Sua pergunta: \"%s\" foi enviada com sucesso e " +
                        "está em análise.", publicacao.getTitulo()));
            } else if (status == 2) {
                if (publicacao.getTipoPublicacao() == 1) {
                    message.setText(String.format("Sua publicação: \"%s\" foi enviada com sucesso e está em análise.",
                            publicacao.getTitulo()));
                } else {
                    message.setText(String.format("Sua pergunta: \"%s\" foi respondida!",
                            publicacao.getTitulo()));
                }
            } else if (status == 3) {
                message.setText(String.format("Sua %s: \"%s\" foi foi aprovada pelo moderador e já está disponível no feed!",
                        publicacao.getTipoPublicacao() == 1 ? "publicação" : "pergunta", publicacao.getTitulo()));
            } else if (status == 4) {
                message.setText(String.format("Sua %s: \"%s\" foi foi recusada pelo moderador por mencionar tema não permitido.",
                        publicacao.getTipoPublicacao() == 1 ? "publicação" : "pergunta", publicacao.getTitulo()));
            } else if (status == 5) {
                message.setText(String.format("Sua %s: \"%s\" foi recusada pelo moderador pois possui palavras inapropriadas.",
                        publicacao.getTipoPublicacao() == 1 ? "publicação" : "pergunta", publicacao.getTitulo()));
            } else if (status == 6) {
                message.setText(String.format("Sua %s: \"%s\" foi recusada pelo moderador pois possui palavras inapropriadas.",
                        publicacao.getTipoPublicacao() == 1 ? "publicação" : "pergunta", publicacao.getTitulo()));
            }
            /**Método para enviar a mensagem criada*/
            Transport.send(message);

            System.out.println("Feito!!!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
