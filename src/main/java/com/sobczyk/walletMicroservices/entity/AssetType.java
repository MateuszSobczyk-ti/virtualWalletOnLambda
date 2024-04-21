package com.sobczyk.walletMicroservices.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AssetType {
    STOCK(1), ETF(2), BOND(3), COMMODITY(4), CRYPTO(5), CASH(6);

    private final int id;

}
