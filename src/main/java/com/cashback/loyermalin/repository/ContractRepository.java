package com.cashback.loyermalin.repository;

import com.cashback.loyermalin.entity.Contract;
import com.cashback.loyermalin.utils.enums.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    Optional<Contract> findByIdAndStatus(Long id, ContractStatus status);

    Optional<Contract> findByTenantIdAndStatus(String tenantId, ContractStatus status);
}
