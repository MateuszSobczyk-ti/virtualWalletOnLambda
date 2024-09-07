package com.sobczyk.walletMicroservices.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "No investor attachment to transaction")
    private Long investorId;
    @NotNull(message = "No asset attachment to transaction")
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
