package com.sobczyk.walletMicroservices.controller;

import com.sobczyk.walletMicroservices.dto.requests.ScheduleRequest;
import com.sobczyk.walletMicroservices.service.DefineScheduleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/report")
public class ReportScheduleController {

    private final DefineScheduleService service;

    @PostMapping
    public ResponseEntity defineSchedule(@RequestBody @Validated ScheduleRequest request) {
        this.service.defineSchedule(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
