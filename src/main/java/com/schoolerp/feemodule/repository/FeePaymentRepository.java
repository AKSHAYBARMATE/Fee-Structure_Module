package com.schoolerp.feemodule.repository;


import com.schoolerp.feemodule.entity.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {
}
