package com.sobczyk.walletMicroservices.controller;

import com.sobczyk.walletMicroservices.dto.requests.PositionPerformanceRequest;
import com.sobczyk.walletMicroservices.dto.requests.TransactionRequest;
import com.sobczyk.walletMicroservices.dto.responses.PostionPerformanceResponse;
import com.sobczyk.walletMicroservices.entity.Investor;
import com.sobczyk.walletMicroservices.service.PositionPerformanceService;
import com.sobczyk.walletMicroservices.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/position")
public class PositionController {

    private final TransactionService transactionService;
    private final PositionPerformanceService positionPerformanceService;

    @PostMapping("/transaction")
    public ResponseEntity<?> saveTransaction(@AuthenticationPrincipal Investor investor,
                                             @Valid @RequestBody TransactionRequest request) {
        return transactionService.saveTransaction(investor, request);
    }

    @GetMapping("/performance")
    public ResponseEntity<List<PostionPerformanceResponse>> getPositionPerformance(@AuthenticationPrincipal Investor investor,
                                                                                   @RequestBody PositionPerformanceRequest request) {
        return positionPerformanceService.getPositionPerformance(investor, request);
    }
}
