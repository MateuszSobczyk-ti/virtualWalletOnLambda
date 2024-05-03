package com.sobczyk.walletMicroservices.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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
    private Long investorId;
    @ManyToOne
    private Asset asset;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private BigDecimal transaction_quantity;
    private BigDecimal purchase_price;
    private BigDecimal trading_fees;
    private LocalDate transaction_date;
    private String description;
    @Transient
    private BigDecimal afterTransactionQuantity;

}
