package com.sobczyk.walletMicroservices.dto.requests;

import jakarta.validation.constraints.NotNull;

public record ScheduleRequest(@NotNull Long investorId, @NotNull Integer reportCycle, @NotNull Integer dayOfCycle,
                              @NotNull Integer timeSeries) {
}
