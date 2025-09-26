package com.schoolerp.feemodule.mapper;

import com.schoolerp.feemodule.entity.Transaction;
import com.schoolerp.feemodule.response.TransactionResponse;

public class TransactionMapper {

    public static TransactionResponse toResponse(Transaction transaction) {
        if (transaction == null) return null;

        return TransactionResponse.builder()
                .id(transaction.getId())
                .transactionId(transaction.getTransactionId())
                .accountHeadName(transaction.getAccountHead().getAccountName())
                .accountHeadCode(transaction.getAccountHead().getAccountCode())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .transactionDate(transaction.getTransactionDate())
                .academicYear(transaction.getAcademicYear())
                .description(transaction.getDescription())
                .status(transaction.getStatus())
                .build();
    }
}
