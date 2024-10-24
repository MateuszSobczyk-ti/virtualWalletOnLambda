package com.sobczyk.walletMicroservices.service;

import com.sobczyk.walletMicroservices.dto.requests.PositionPerformanceRequest;
import com.sobczyk.walletMicroservices.entity.ReportCycle;
import com.sobczyk.walletMicroservices.entity.ReportSchedule;
import com.sobczyk.walletMicroservices.repository.ReportScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
public class Scheduler {

    private final ReportScheduleRepository repository;
    private final RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "0 00 19 * * ?")
    public void schedulePerformanceReport() {
        LocalDate dateNow = LocalDate.now();
        repository.getReportSchedule(dateNow.getDayOfWeek().getValue(), dateNow.getDayOfMonth(),
                        ReportCycle.WEEK, ReportCycle.MONTH).stream()
                            .map(this::mapToPosPerfRequest)
                            .forEach(this::sendMessage);
        log.info("Messages added on queue" );
    }

    private PositionPerformanceRequest mapToPosPerfRequest(ReportSchedule reportSchedule) {
        return new PositionPerformanceRequest(reportSchedule.getInvestorId(), reportSchedule.getTimeSeries().getId());
    }

    private void sendMessage(PositionPerformanceRequest message) {
        rabbitTemplate.convertAndSend("x.performance-core", "performance-core", message);
    }
}
