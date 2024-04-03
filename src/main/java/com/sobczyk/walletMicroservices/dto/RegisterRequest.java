package com.sobczyk.walletMicroservices.dto;

public record RegisterRequest(String firstname, String lastname, String email, String password) {
}
