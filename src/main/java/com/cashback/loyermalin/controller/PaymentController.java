package com.cashback.loyermalin.controller;

import com.cashback.loyermalin.dto.PaymentRequest;
import com.cashback.loyermalin.dto.PaymentResponse;
import com.cashback.loyermalin.service.CashbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final CashbackService cashbackService;

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@Valid @RequestBody PaymentRequest request, Principal principal) {
        log.info("Received payment processing request from user {}", principal.getName());

        PaymentResponse response = cashbackService.processPayment(request, principal.getName());

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/cashback/balance")
    public ResponseEntity<BigDecimal> getCashbackBalance(Principal principal) {
        log.info("Request for cashback balance from user {}", principal.getName());
        BigDecimal balance = cashbackService.getCashbackBalance(principal.getName());
        return ResponseEntity.ok(balance);
    }
}
