package com.schoolerp.feemodule.repository;

import com.schoolerp.feemodule.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByIsDeletedFalse(Pageable pageable);

    Page<Transaction> findByIsDeletedFalseAndTransactionIdContainingIgnoreCase(String transactionId, Pageable pageable);

    Page<Transaction> findByIsDeletedFalseAndType(Transaction.TransactionType type, Pageable pageable);

    Page<Transaction> findByIsDeletedFalseAndStatus(Transaction.TransactionStatus status, Pageable pageable);

    Page<Transaction> findByIsDeletedFalseAndTypeAndStatus(Transaction.TransactionType type, Transaction.TransactionStatus status, Pageable pageable);

    Page<Transaction> findByIsDeletedFalseAndTransactionIdContainingIgnoreCaseAndTypeAndStatus(String transactionId, Transaction.TransactionType type, Transaction.TransactionStatus status, Pageable pageable);

    // New for Academic Year
    Page<Transaction> findByIsDeletedFalseAndAcademicYear(Integer academicYear, Pageable pageable);

    Page<Transaction> findByIsDeletedFalseAndTypeAndAcademicYear(Transaction.TransactionType type, Integer academicYear, Pageable pageable);

    Page<Transaction> findByIsDeletedFalseAndStatusAndAcademicYear(Transaction.TransactionStatus status, Integer academicYear, Pageable pageable);

    Page<Transaction> findByIsDeletedFalseAndTypeAndStatusAndAcademicYear(Transaction.TransactionType type, Transaction.TransactionStatus status, Integer academicYear, Pageable pageable);

    Page<Transaction> findByIsDeletedFalseAndTransactionIdContainingIgnoreCaseAndAcademicYear(String transactionId, Integer academicYear, Pageable pageable);

    Page<Transaction> findByIsDeletedFalseAndTransactionIdContainingIgnoreCaseAndTypeAndStatusAndAcademicYear(String transactionId, Transaction.TransactionType type, Transaction.TransactionStatus status, Integer academicYear, Pageable pageable);

    Optional<Transaction> findByTransactionId(String transactionId);

    boolean existsByTransactionIdAndIsDeletedFalse(String transactionId);

    Page<Transaction> findByIsDeletedFalseAndTransactionIdContainingIgnoreCaseAndTypeAndStatusAndAcademicYearAndAccountHeadId(String search, Transaction.TransactionType type, Transaction.TransactionStatus status, Integer academicYear, Long accountHeadId, Pageable pageable);

    Page<Transaction> findByIsDeletedFalseAndTransactionIdContainingIgnoreCaseAndAcademicYearAndAccountHeadId(String search, Integer academicYear, Long accountHeadId, Pageable pageable);

    Page<Transaction> findByIsDeletedFalseAndTypeAndStatusAndAcademicYearAndAccountHeadId(Transaction.TransactionType type, Transaction.TransactionStatus status, Integer academicYear, Long accountHeadId, Pageable pageable);

    Page<Transaction> findByIsDeletedFalseAndTypeAndAcademicYearAndAccountHeadId(Transaction.TransactionType type, Integer academicYear, Long accountHeadId, Pageable pageable);

    Page<Transaction> findByIsDeletedFalseAndStatusAndAcademicYearAndAccountHeadId(Transaction.TransactionStatus status, Integer academicYear, Long accountHeadId, Pageable pageable);

    Page<Transaction> findByIsDeletedFalseAndAcademicYearAndAccountHeadId(Integer academicYear, Long accountHeadId, Pageable pageable);

    Page<Transaction> findByIsDeletedFalseAndAccountHeadId(Long accountHeadId, Pageable pageable);
}
