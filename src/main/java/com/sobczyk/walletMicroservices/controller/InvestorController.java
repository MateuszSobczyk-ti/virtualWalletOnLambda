package com.sobczyk.walletMicroservices.controller;

import com.sobczyk.walletMicroservices.dto.InvestorDto;
import com.sobczyk.walletMicroservices.securtiy.InvestorServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/investors")
public class InvestorController {

    private final InvestorServiceImpl investorService;

//    @PostMapping
//    public ResponseEntity<String> addInvestor(@RequestBody InvestorDto investorDto, @RequestParam String roleName) {
//        return investorService.registerInvestor(investorDto, roleName);
//    }
//
    @GetMapping
    public ResponseEntity<List<InvestorDto>> getAllInvestors() {
        List<InvestorDto> investors = investorService.findAllInvestors();
        return ResponseEntity.ok(investors);
    }
}
