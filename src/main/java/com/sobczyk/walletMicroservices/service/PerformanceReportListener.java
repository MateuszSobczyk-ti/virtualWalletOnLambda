package com.sobczyk.walletMicroservices.service;

import com.sobczyk.walletMicroservices.dto.responses.PositionPerformanceResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PerformanceReportListener {

    private final PerformancePDFGenerator pdfGenerator;

    @RabbitListener(queues = {"q.performance-report"})
    public void generatePerfReport(PositionPerformanceResponse performanceResponse) {
        log.info("received new message from rabbit report queue");
        this.pdfGenerator.generatePdf(performanceResponse, "PerformanceResponse.pdf");
    }
}
