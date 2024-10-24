package com.sobczyk.walletMicroservices.entity;

import com.sobczyk.walletMicroservices.position.performance.TimeSeries;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "report_schedule")
public class ReportSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long investorId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private ReportCycle reportCycle;
    @NotNull
    private Integer dayOfCycle;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TimeSeries timeSeries;
}
