package com.sobczyk.walletMicroservices.dto.responses;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record TransactionResponse(String ticker, String assetType, String transactionType,
                                  BigDecimal quantity, BigDecimal price, BigDecimal fees,
                                  LocalDate date, String description) {
}
