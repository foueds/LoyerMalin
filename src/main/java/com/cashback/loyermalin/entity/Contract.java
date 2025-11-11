package com.cashback.loyermalin.entity;

import com.cashback.loyermalin.utils.enums.ContractStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contracts", indexes = {
    @Index(name = "idx_contract_tenant_id_status", columnList = "tenantId, status")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tenantId;

    @Column(nullable = false)
    private String ownerId;

    @Column(nullable = false)
    private BigDecimal rentAmount;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Integer dueDay; // Day of the month for payment (e.g., 5 for the 5th)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractStatus status;

    @Column(nullable = false)
    private BigDecimal cashbackRate; // Cashback percentage (e.g., 2.5 for 2.5%)
}