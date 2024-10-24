package com.sobczyk.walletMicroservices.service;

import com.sobczyk.walletMicroservices.dto.requests.ScheduleRequest;
import com.sobczyk.walletMicroservices.entity.ReportCycle;
import com.sobczyk.walletMicroservices.entity.ReportSchedule;
import com.sobczyk.walletMicroservices.position.performance.TimeSeries;
import com.sobczyk.walletMicroservices.repository.ReportScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefineScheduleService {

    private final ReportScheduleRepository repository;

    public void defineSchedule(ScheduleRequest request) {
        ReportSchedule reportSchedule = ReportSchedule.builder()
                .investorId(request.investorId())
                .reportCycle(ReportCycle.getById(request.reportCycle()))
                .dayOfCycle(request.dayOfCycle())
                .timeSeries(TimeSeries.getById(request.timeSeries()))
                .build();
        repository.save(reportSchedule);
    }
}
