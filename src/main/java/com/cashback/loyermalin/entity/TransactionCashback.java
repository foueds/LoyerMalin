package com.cashback.loyermalin.entity;

import com.cashback.loyermalin.utils.enums.TransactionStatus;
import com.cashback.loyermalin.utils.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cashback_transactions", indexes = {
    @Index(name = "idx_transaction_userid_status", columnList = "userId, status")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCashback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long contractId;

    @Column(nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    private String transactionReference; // Rent payment reference
}