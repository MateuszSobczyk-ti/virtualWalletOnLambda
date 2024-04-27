package com.sobczyk.walletMicroservices.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
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
    private Asset asset;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private BigDecimal transaction_quantity;
    private BigDecimal after_transaction_quantity; //autocomplete!
    private BigDecimal purchase_price;
    private BigDecimal total_amount; //autocomplete!
    private BigDecimal trading_fees;
    private LocalDateTime transaction_date;
    private String description;

}
