package com.schoolerp.feemodule.service;


import com.schoolerp.feemodule.entity.Transaction;
import com.schoolerp.feemodule.entity.Transaction.TransactionStatus;
import com.schoolerp.feemodule.entity.Transaction.TransactionType;
import com.schoolerp.feemodule.request.TransactionRequest;
import com.schoolerp.feemodule.response.TransactionResponse;
import com.schoolerp.feemodule.response.TransactionSummaryResponse;
import org.springframework.data.domain.Page;

public interface TransactionService {

    TransactionResponse getTransactionById(Long id);

    TransactionResponse getTransactionByTransactionId(String transactionId);

    TransactionSummaryResponse getAllTransactions(
            int page,
            int size,
            String search,
            TransactionType type,
            TransactionStatus status,Integer academicYearId,Long accountHeadId,Integer studentId
    );

    Transaction createTransaction(TransactionRequest request);
}
