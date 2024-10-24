package com.sobczyk.walletMicroservices.service;

import com.sobczyk.walletMicroservices.dto.requests.PositionPerformanceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PerformanceReportListener {

    @RabbitListener(queues = {"q.performance-report"})
    public void generatePerfReport(List<PositionPerformanceRequest> requestList) {
        log.info("received new message from rabbit queue: " + requestList);
        System.out.println(requestList.size());
    }
}
