package com.sobczyk.walletMicroservices.dto.responses;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {
}
