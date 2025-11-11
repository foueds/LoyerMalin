package com.cashback.loyermalin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private boolean success;
    private String message;
    private BigDecimal montantCashback;
    private Long transactionId;
    private boolean eligible;
}