package com.sobczyk.walletMicroservices.repository;

import com.sobczyk.walletMicroservices.entity.ReportCycle;
import com.sobczyk.walletMicroservices.entity.ReportSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportScheduleRepository extends JpaRepository<ReportSchedule, Long> {

    @Query("select r from ReportSchedule r where (r.reportCycle = :weekCycle and r.dayOfCycle = :dayOfWeek) " +
            " or (r.reportCycle = :monthCycle and r.dayOfCycle = :dayOfMonth)")
    List<ReportSchedule> getReportSchedule(int dayOfWeek, int dayOfMonth, ReportCycle weekCycle, ReportCycle monthCycle);
}
