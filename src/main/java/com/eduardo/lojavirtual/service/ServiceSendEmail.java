package com.eduardo.lojavirtual.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class ServiceSendEmail {

    private String userName = "eduardodevjavaweb@gmail.com";
    private String senha = "uzfwpjwevqbtovpz";

    @Async
    public void enviarEmailHtml(String assunto, String menssagem, String emailDestino) throws UnsupportedEncodingException, MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls", "false");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(properties, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, senha);
            }

        });

        session.setDebug(true);

        Address[] toUser = InternetAddress.parse(emailDestino);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userName, "Eduardo - Java Web", "UTF-8"));
        message.setRecipients(Message.RecipientType.TO, toUser);
        message.setSubject(assunto);
        message.setText(menssagem);

        Transport.send(message);
    }

}
