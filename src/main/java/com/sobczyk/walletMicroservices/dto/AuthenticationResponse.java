package com.sobczyk.walletMicroservices.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {
}
