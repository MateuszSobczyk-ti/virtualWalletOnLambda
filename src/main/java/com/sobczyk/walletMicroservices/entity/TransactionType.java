package com.sobczyk.walletMicroservices.entity;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum TransactionType {
    BUY(1), SELL(2), DEPOSIT(3), WITHDRAW(4);

    private final int id;

    public static TransactionType getById(Integer id) {
        return Arrays.stream(TransactionType.values())
                .filter(v -> v.id == id)
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("no matching transaction type for id: " + id));
    }
}
