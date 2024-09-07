package com.sobczyk.walletMicroservices.dto.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionRequest(String ticker,
                                 @NotNull(message = "assetType cannot be empty")
                                 @Min(message = "assetType value must be between 1 and 6", value = 1)
                                 @Max(message = "assetType value must be between 1 and 6", value = 6)
                                 Integer assetType,
                                 @NotNull(message = "transactionType cannot be empty")
                                 @Min(message = "transactionType value must be between 1 and 4", value = 1)
                                 @Max(message = "transactionType value must be between 1 and 4", value = 4)
                                 Integer transactionType,
                                 @NotNull(message = "quantity cannot be empty")
                                 @Min(message = "quantity value must be positive", value = 0)
                                 BigDecimal quantity,
                                 @Min(message = "price value must be positive", value = 0)
                                 BigDecimal price,
                                 BigDecimal fees,
                                 @NotNull(message = "date cannot be empty")
                                 String date,
                                 String description) {
}
