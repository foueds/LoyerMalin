package com.cashback.loyermalin.service;

import com.cashback.loyermalin.dto.PaymentRequest;
import com.cashback.loyermalin.dto.PaymentResponse;
import com.cashback.loyermalin.entity.Contract;
import com.cashback.loyermalin.entity.TransactionCashback;
import com.cashback.loyermalin.exception.ContractNotFoundException;
import com.cashback.loyermalin.repository.ContractRepository;
import com.cashback.loyermalin.repository.TransactionCashbackRepository;
import com.cashback.loyermalin.utils.enums.ContractStatus;
import com.cashback.loyermalin.utils.enums.TransactionStatus;
import com.cashback.loyermalin.utils.enums.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CashbackService {

    private final ContractRepository contractRepository;
    private final TransactionCashbackRepository transactionRepository;

    @Transactional
    public PaymentResponse processPayment(PaymentRequest request, String userId) {
        log.info("Processing payment for contract {} by user {}", request.getContractId(), userId);

        Contract contract = contractRepository.findByIdAndStatus(request.getContractId(), ContractStatus.ACTIVE)
                .orElseThrow(() -> new ContractNotFoundException("Active contract not found for ID: " + request.getContractId()));

        if (!contract.getTenantId().equals(userId)) {
            log.warn("Unauthorized payment attempt by user {} for contract {}", userId, contract.getId());
            return new PaymentResponse(false, "You are not authorized to make this payment.", BigDecimal.ZERO, null, false);
        }

        if (request.getPaidAmount().compareTo(contract.getRentAmount()) < 0) {
            log.warn("Insufficient amount: {} paid, {} expected for contract {}", request.getPaidAmount(), contract.getRentAmount(), contract.getId());
            return new PaymentResponse(false, "Paid amount is less than the rent amount.", BigDecimal.ZERO, null, false);
        }

        if (!isEligibleForCashback(request.getPaymentDate(), contract.getDueDay())) {
            log.info("Payment for contract {} is not eligible for cashback (payment date: {}).", contract.getId(), request.getPaymentDate());
            return new PaymentResponse(true, "Payment recorded but not eligible for cashback (made after the due date).", BigDecimal.ZERO, null, false);
        }

        BigDecimal cashbackAmount = calculateCashbackAmount(contract.getRentAmount(), contract.getCashbackRate());

        TransactionCashback transaction = createEarnTransaction(contract, userId, cashbackAmount, request.getTransactionReference());
        TransactionCashback savedTransaction = transactionRepository.save(transaction);

        log.info("Cashback of {} EUR credited for user {} (Transaction ID: {}).", cashbackAmount, userId, savedTransaction.getId());

        return new PaymentResponse(true, "Payment processed successfully. Cashback credited!", cashbackAmount, savedTransaction.getId(), true);
    }

    private boolean isEligibleForCashback(LocalDate paymentDate, Integer dueDayOfMonth) {
        LocalDate dueDate = paymentDate.withDayOfMonth(dueDayOfMonth);
        return !paymentDate.isAfter(dueDate);
    }

    private BigDecimal calculateCashbackAmount(BigDecimal rentAmount, BigDecimal cashbackRate) {
        if (cashbackRate == null || cashbackRate.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal hundred = new BigDecimal("100");
        return rentAmount.multiply(cashbackRate).divide(hundred, 2, RoundingMode.HALF_UP);
    }

    private TransactionCashback createEarnTransaction(Contract contract, String userId, BigDecimal cashbackAmount, String paymentReference) {
        TransactionCashback transaction = new TransactionCashback();
        transaction.setContractId(contract.getId());
        transaction.setUserId(userId);
        transaction.setType(TransactionType.EARN);
        transaction.setAmount(cashbackAmount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setDescription("Cashback for rent payment - Contract #" + contract.getId());
        transaction.setStatus(TransactionStatus.VALID);
        transaction.setTransactionReference(paymentReference);
        return transaction;
    }

    public BigDecimal getCashbackBalance(String userId) {
        BigDecimal totalGains = transactionRepository.calculateTotalGains(userId, TransactionStatus.VALID, TransactionType.EARN);
        BigDecimal totalUsages = transactionRepository.calculateTotalUsages(userId, TransactionStatus.VALID, TransactionType.SPEND);
        return totalGains.subtract(totalUsages);
    }
}