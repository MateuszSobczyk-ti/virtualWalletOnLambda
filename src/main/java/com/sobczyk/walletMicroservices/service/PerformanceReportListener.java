package com.sobczyk.walletMicroservices.service;

import com.sobczyk.walletMicroservices.dto.responses.PositionPerformanceResponse;
import com.sobczyk.walletMicroservices.service.email.PerformanceEmailSender;
import com.sobczyk.walletMicroservices.service.pdf.PerformancePDFGenerator;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PerformanceReportListener {

    private final PerformancePDFGenerator pdfGenerator;
    private final PerformanceEmailSender emailSender;

    @RabbitListener(queues = {"q.performance-report"})
    public void generatePerfReport(PositionPerformanceResponse response) throws MessagingException {
        log.info("received new message from rabbit report queue");
        byte[] pdfBytes = this.pdfGenerator.generatePdf(response);
        this.emailSender.sendEmail(response.getInvestorDto().email(), response.getInvestorDto().firstname(), pdfBytes);
    }
}
