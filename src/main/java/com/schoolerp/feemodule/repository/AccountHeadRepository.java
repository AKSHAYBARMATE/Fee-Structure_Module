package com.schoolerp.feemodule.repository;

import com.schoolerp.feemodule.entity.AccountHead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AccountHeadRepository extends JpaRepository<AccountHead,Long> {
    Optional<AccountHead> findByIdAndIsDeletedFalse(Long id);

    Page<AccountHead> findAllByIsDeletedFalse(Pageable pageable);

    Page<AccountHead> findAllByIsDeletedFalseAndAccountType(AccountHead.AccountType accountType, Pageable pageable);
}
