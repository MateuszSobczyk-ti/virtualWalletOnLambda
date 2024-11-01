package com.sobczyk.walletMicroservices.service.email;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.stereotype.Service;

@Service
public class PerformanceEmailSender extends EmailSender {
    @Override
    public void generateContent(Message message, String recipientEmail, String recipientName, byte[] pdfBytes) throws MessagingException {
        message.setFrom(new InternetAddress("hello@demomailtrap.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Performance report");

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent("Dear " + recipientName + ",\n\nwe are sending you performance of your investment.",
                "text/html; charset=utf-8");

        MimeBodyPart attachmentPart = new MimeBodyPart();
        DataSource source = new ByteArrayDataSource(pdfBytes, "application/pdf");
        attachmentPart.setDataHandler(new DataHandler(source));
        attachmentPart.setFileName("performance_report.pdf");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        multipart.addBodyPart(attachmentPart);

        message.setContent(multipart);
    }
}
