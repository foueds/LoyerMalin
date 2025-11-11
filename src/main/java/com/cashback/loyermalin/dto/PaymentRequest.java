package com.cashback.loyermalin.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    @NotNull(message = "Contract ID cannot be null.")
    private Long contractId;

    @NotNull(message = "Paid amount cannot be null.")
    @Positive(message = "Paid amount must be positive.")
    private BigDecimal paidAmount;

    @NotNull(message = "Payment date cannot be null.")
    private LocalDate paymentDate;

    private String transactionReference;
}