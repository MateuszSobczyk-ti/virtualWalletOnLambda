package com.sobczyk.walletMicroservices.service.email;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessagingException;

import java.util.Properties;

public abstract class EmailSender {

    @Value("${mailtrap.username}")
    private String mailTrapUsername;

    @Value("${mailtrap.password}")
    private String mailTrapPassword;

    @Value("${mailtrap.host}")
    private String mailTrapHost;

    @Value("${mailtrap.port}")
    private String mailTrapPort;

    public void sendEmail(String recipientEmail, String recipientName, byte[] pdfBytes) throws MessagingException, jakarta.mail.MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", mailTrapHost);
        props.put("mail.smtp.port", mailTrapPort);
        props.put("mail.smtp.ssl.trust", mailTrapHost);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication()
            {
                return new jakarta.mail.PasswordAuthentication(mailTrapUsername, mailTrapPassword);
            }
        });

        Message message = new MimeMessage(session);
        this.generateContent(message, recipientEmail, recipientName, pdfBytes);
        Transport.send(message);
    }

    public abstract void generateContent(Message message, String recipientEmail, String recipientName, byte[] pdfBytes)
            throws jakarta.mail.MessagingException;
}
