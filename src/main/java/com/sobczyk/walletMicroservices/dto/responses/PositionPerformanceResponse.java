package com.sobczyk.walletMicroservices.dto.responses;

import com.sobczyk.walletMicroservices.dto.PositionPerformanceDto;
import com.sobczyk.walletMicroservices.position.performance.PositionPerfValue;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PositionPerformanceResponse {

    private List<PositionPerformanceDto> allTimePositions;
    private List<PositionPerformanceDto> currentPositions;
    private List<PositionPerformanceDto> allTimeOverall;
    private PositionPerfValue overall;
    private PositionPerfValue cash;
    private List<TransactionResponse> transactions;

    public PositionPerformanceResponse() {
        this.currentPositions = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }
}
