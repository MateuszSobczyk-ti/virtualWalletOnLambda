package com.sobczyk.walletMicroservices.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AssetType assetType;
    private String ticker;
    private Double amount;
    private LocalDateTime opened_at;
    private LocalDateTime closed_at;
}
