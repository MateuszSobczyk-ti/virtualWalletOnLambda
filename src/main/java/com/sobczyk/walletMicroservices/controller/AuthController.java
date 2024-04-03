package com.sobczyk.walletMicroservices.controller;

import com.sobczyk.walletMicroservices.dto.AuthenticationRequest;
import com.sobczyk.walletMicroservices.dto.AuthenticationResponse;
import com.sobczyk.walletMicroservices.dto.RegisterRequest;
import com.sobczyk.walletMicroservices.securtiy.InvestorServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final InvestorServiceImpl service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.registerInvestor(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
