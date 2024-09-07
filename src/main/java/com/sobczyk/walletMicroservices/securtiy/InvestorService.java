package com.sobczyk.walletMicroservices.securtiy;

import com.sobczyk.walletMicroservices.dto.InvestorDto;
import com.sobczyk.walletMicroservices.dto.requests.AuthenticationRequest;
import com.sobczyk.walletMicroservices.dto.requests.RegisterRequest;
import com.sobczyk.walletMicroservices.dto.responses.AuthenticationResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InvestorService {
    ResponseEntity<?> registerInvestor(RegisterRequest request);

    ResponseEntity<?> findInvestorByEmail(String email);

    List<InvestorDto> findAllInvestors();

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
