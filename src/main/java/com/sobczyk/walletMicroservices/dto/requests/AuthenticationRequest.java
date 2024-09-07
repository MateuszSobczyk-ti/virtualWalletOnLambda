package com.sobczyk.walletMicroservices.dto.requests;

public record AuthenticationRequest(String email, String password) {
}
