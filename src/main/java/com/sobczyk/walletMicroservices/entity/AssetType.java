package com.sobczyk.walletMicroservices.entity;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum AssetType {
    STOCK(1), ETF(2), BOND(3), COMMODITY(4), CRYPTO(5), CASH(6);

    private final int id;

    public static AssetType getById(Integer id) {
        return Arrays.stream(AssetType.values())
                .filter(v -> v.id == id)
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("no matching asset type for id: " + id));
    }

}
