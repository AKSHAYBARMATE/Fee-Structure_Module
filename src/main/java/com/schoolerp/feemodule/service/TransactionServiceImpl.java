package com.schoolerp.feemodule.service;

import com.schoolerp.feemodule.entity.AccountHead;
import com.schoolerp.feemodule.entity.Transaction;
import com.schoolerp.feemodule.entity.Transaction.TransactionStatus;
import com.schoolerp.feemodule.entity.Transaction.TransactionType;
import com.schoolerp.feemodule.exception.CustomException;
import com.schoolerp.feemodule.mapper.TransactionMapper;
import com.schoolerp.feemodule.repository.AccountHeadRepository;
import com.schoolerp.feemodule.repository.TransactionRepository;
import com.schoolerp.feemodule.request.TransactionRequest;
import com.schoolerp.feemodule.response.TransactionResponse;
import com.schoolerp.feemodule.response.TransactionSummaryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountHeadRepository accountHeadRepository;

    @Override
    public TransactionResponse getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));

        if (Boolean.TRUE.equals(transaction.getIsDeleted())) {
            throw new RuntimeException("Transaction is deleted");
        }

        return TransactionMapper.toResponse(transaction);
    }

    @Override
    public TransactionResponse getTransactionByTransactionId(String transactionId) {
        Transaction transaction = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new CustomException(
                        "Transaction not found",
                        "TXN_NOT_FOUND",
                        "No transaction exists with transactionId: " + transactionId
                ));

        if (Boolean.TRUE.equals(transaction.getIsDeleted())) {
            throw new CustomException(
                    "Transaction is deleted",
                    "TXN_DELETED",
                    "Transaction with transactionId " + transactionId + " has been marked as deleted"
            );
        }

        return TransactionMapper.toResponse(transaction);
    }



    @Override
    public TransactionSummaryResponse getAllTransactions(
            int page,
            int size,
            String search,
            TransactionType type,
            TransactionStatus status,
            Integer academicYear,
            Long accountHeadId // new filter
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactions;

        boolean hasSearch = (search != null && !search.isEmpty());
        boolean hasType = (type != null);
        boolean hasStatus = (status != null);
        boolean hasAcademicYear = (academicYear != null);
        boolean hasAccountHead = (accountHeadId != null);

        // Filter logic
        if (hasSearch && hasType && hasStatus && hasAcademicYear && hasAccountHead) {
            transactions = transactionRepository
                    .findByIsDeletedFalseAndTransactionIdContainingIgnoreCaseAndTypeAndStatusAndAcademicYearAndAccountHeadId(
                            search, type, status, academicYear, accountHeadId, pageable);
        } else if (hasSearch && hasAcademicYear && hasAccountHead) {
            transactions = transactionRepository
                    .findByIsDeletedFalseAndTransactionIdContainingIgnoreCaseAndAcademicYearAndAccountHeadId(
                            search, academicYear, accountHeadId, pageable);
        } else if (hasType && hasStatus && hasAcademicYear && hasAccountHead) {
            transactions = transactionRepository
                    .findByIsDeletedFalseAndTypeAndStatusAndAcademicYearAndAccountHeadId(
                            type, status, academicYear, accountHeadId, pageable);
        } else if (hasType && hasAcademicYear && hasAccountHead) {
            transactions = transactionRepository
                    .findByIsDeletedFalseAndTypeAndAcademicYearAndAccountHeadId(
                            type, academicYear, accountHeadId, pageable);
        } else if (hasStatus && hasAcademicYear && hasAccountHead) {
            transactions = transactionRepository
                    .findByIsDeletedFalseAndStatusAndAcademicYearAndAccountHeadId(
                            status, academicYear, accountHeadId, pageable);
        } else if (hasAcademicYear && hasAccountHead) {
            transactions = transactionRepository
                    .findByIsDeletedFalseAndAcademicYearAndAccountHeadId(academicYear, accountHeadId, pageable);
        } else if (hasAccountHead) {
            transactions = transactionRepository
                    .findByIsDeletedFalseAndAccountHeadId(accountHeadId, pageable);
        } else {
            // existing filters without accountHead
            if (hasSearch && hasType && hasStatus && hasAcademicYear) {
                transactions = transactionRepository
                        .findByIsDeletedFalseAndTransactionIdContainingIgnoreCaseAndTypeAndStatusAndAcademicYear(
                                search, type, status, academicYear, pageable);
            } else if (hasSearch && hasAcademicYear) {
                transactions = transactionRepository
                        .findByIsDeletedFalseAndTransactionIdContainingIgnoreCaseAndAcademicYear(search, academicYear, pageable);
            } else if (hasType && hasStatus && hasAcademicYear) {
                transactions = transactionRepository
                        .findByIsDeletedFalseAndTypeAndStatusAndAcademicYear(type, status, academicYear, pageable);
            } else if (hasType && hasAcademicYear) {
                transactions = transactionRepository
                        .findByIsDeletedFalseAndTypeAndAcademicYear(type, academicYear, pageable);
            } else if (hasStatus && hasAcademicYear) {
                transactions = transactionRepository
                        .findByIsDeletedFalseAndStatusAndAcademicYear(status, academicYear, pageable);
            } else if (hasAcademicYear) {
                transactions = transactionRepository
                        .findByIsDeletedFalseAndAcademicYear(academicYear, pageable);
            } else if (hasSearch && hasType && hasStatus) {
                transactions = transactionRepository
                        .findByIsDeletedFalseAndTransactionIdContainingIgnoreCaseAndTypeAndStatus(search, type, status, pageable);
            } else if (hasSearch) {
                transactions = transactionRepository
                        .findByIsDeletedFalseAndTransactionIdContainingIgnoreCase(search, pageable);
            } else if (hasType && hasStatus) {
                transactions = transactionRepository
                        .findByIsDeletedFalseAndTypeAndStatus(type, status, pageable);
            } else if (hasType) {
                transactions = transactionRepository
                        .findByIsDeletedFalseAndType(type, pageable);
            } else if (hasStatus) {
                transactions = transactionRepository
                        .findByIsDeletedFalseAndStatus(status, pageable);
            } else {
                transactions = transactionRepository.findByIsDeletedFalse(pageable);
            }
        }

        // Mapping and summary
        List<TransactionResponse> transactionResponses = transactions.getContent().stream()
                .map(TransactionMapper::toResponse)
                .toList();

        BigDecimal totalCredits = transactions.getContent().stream()
                .filter(t -> t.getType() == Transaction.TransactionType.CREDIT)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDebits = transactions.getContent().stream()
                .filter(t -> t.getType() == Transaction.TransactionType.DEBIT)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return TransactionSummaryResponse.builder()
                .transactions(transactionResponses)
                .totalTransactions(transactions.getTotalElements())
                .totalCredits(totalCredits)
                .totalDebits(totalDebits)
                .netBalance(totalCredits.subtract(totalDebits))
                .pageNumber(transactions.getNumber())
                .pageSize(transactions.getSize())
                .totalPages(transactions.getTotalPages())
                .build();
    }


    @Override
    public Transaction createTransaction(TransactionRequest request) {
        log.info("Creating transaction with ID: {}", request.getTransactionId());

        // ✅ Check for duplicate transactionId
        boolean exists = transactionRepository.existsByTransactionIdAndIsDeletedFalse(request.getTransactionId());
        if (exists) {
            log.error("Transaction with ID {} already exists", request.getTransactionId());
            throw new CustomException(
                    "Transaction ID already exists",
                    "TXN_DUPLICATE",
                    "A transaction with ID " + request.getTransactionId() + " already exists in the system."
            );
        }

        AccountHead accountHead = accountHeadRepository.findByIdAndIsDeletedFalse(request.getAccountHeadId())
                .orElseThrow(() -> {
                    log.error("AccountHead not found with ID: {}", request.getAccountHeadId());
                    return new CustomException(
                            "AccountHead not found",
                            "ACCOUNTHEAD_NOT_FOUND",
                            "No active AccountHead found with ID " + request.getAccountHeadId()
                    );
                });

        Transaction transaction = Transaction.builder()
                .transactionId(request.getTransactionId())
                .accountHead(accountHead)
                .type(request.getType())
                .amount(request.getAmount())
                .transactionDate(request.getTransactionDate())
                .academicYear(request.getAcademicYear())
                .description(request.getDescription())
                .status(request.getStatus())
                .isDeleted(false)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);
        log.info("Transaction created successfully with DB ID: {}", savedTransaction.getId());

        return savedTransaction;
    }


}
