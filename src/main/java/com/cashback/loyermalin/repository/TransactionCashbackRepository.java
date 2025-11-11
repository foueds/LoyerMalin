
package com.cashback.loyermalin.repository;

import com.cashback.loyermalin.entity.TransactionCashback;
import com.cashback.loyermalin.utils.enums.TransactionStatus;
import com.cashback.loyermalin.utils.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionCashbackRepository extends JpaRepository<TransactionCashback, Long> {

    List<TransactionCashback> findByUserIdAndStatus(String userId, TransactionStatus status);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionCashback t " +
            "WHERE t.userId = :userId AND t.status = :status AND t.type = :type")
    BigDecimal calculateTotalGains(@Param("userId") String userId,
                                   @Param("status") TransactionStatus status,
                                   @Param("type") TransactionType type);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionCashback t " +
            "WHERE t.userId = :userId AND t.status = :status AND t.type = :type")
    BigDecimal calculateTotalUsages(@Param("userId") String userId,
                                    @Param("status") TransactionStatus status,
                                    @Param("type") TransactionType type);
}