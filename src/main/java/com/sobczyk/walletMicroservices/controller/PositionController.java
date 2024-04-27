package com.sobczyk.walletMicroservices.controller;

import com.sobczyk.walletMicroservices.dto.requests.TransactionRequest;
import com.sobczyk.walletMicroservices.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/position")
public class PositionController {

    private final TransactionService transactionService;

    @PostMapping("/transaction")
    public ResponseEntity<?> saveTransaction(@Valid @RequestBody TransactionRequest request) {
        return transactionService.saveTransaction(request);
    }
}
