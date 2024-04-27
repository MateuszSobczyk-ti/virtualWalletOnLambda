package com.sobczyk.walletMicroservices.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "asset")
public class Asset {

    @Id
    private String ticker;
    @Enumerated(EnumType.STRING)
    private AssetType assetType;
}
