package com.sobczyk.walletMicroservices.dto.requests;

public record RegisterRequest(String firstname, String lastname, String email, String password) {
}
