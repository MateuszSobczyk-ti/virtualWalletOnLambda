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
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Position position;
    private Double transaction_quantity;
    private Double after_transaction_quantity; //autocomplete!
    private Double purchase_price;
    private Double total_amount; //autocomplete!
    private Double trading_fees;
    private LocalDateTime transaction_date;
    private String description;

}
