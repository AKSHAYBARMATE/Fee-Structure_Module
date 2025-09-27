package com.schoolerp.feemodule.controller;

import com.schoolerp.feemodule.entity.Transaction;
import com.schoolerp.feemodule.entity.Transaction.TransactionStatus;
import com.schoolerp.feemodule.entity.Transaction.TransactionType;
import com.schoolerp.feemodule.request.TransactionRequest;
import com.schoolerp.feemodule.response.StandardResponse;
import com.schoolerp.feemodule.response.TransactionResponse;
import com.schoolerp.feemodule.response.TransactionSummaryResponse;
import com.schoolerp.feemodule.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/fee-structures-module")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("getTransactionById/{id}")
    public ResponseEntity<StandardResponse<TransactionResponse>> getTransactionById(@PathVariable Long id) {
        TransactionResponse transaction = transactionService.getTransactionById(id);
        StandardResponse<TransactionResponse> response = StandardResponse.<TransactionResponse>builder()
                .success(true)
                .message("Transaction fetched successfully")
                .data(transaction)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getTransactionByTransactionNumber/{transactionId}")
    public ResponseEntity<StandardResponse<TransactionResponse>> getTransactionByTransactionId(@PathVariable String transactionId) {
        TransactionResponse transaction = transactionService.getTransactionByTransactionId(transactionId);
        StandardResponse<TransactionResponse> response = StandardResponse.<TransactionResponse>builder()
                .success(true)
                .message("Transaction fetched successfully")
                .data(transaction)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllTransactions")
    public ResponseEntity<StandardResponse<TransactionSummaryResponse>> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) TransactionStatus status,
            @RequestParam(required = false) Integer academicYear,
            @RequestParam(required = false) Long accountHeadId
    ) {
        TransactionSummaryResponse summary = transactionService.getAllTransactions(page, size, search, type, status, academicYear,accountHeadId);

        StandardResponse<TransactionSummaryResponse> response = StandardResponse.<TransactionSummaryResponse>builder()
                .success(true)
                .message("Transactions fetched successfully")
                .data(summary)
                .timestamp(java.time.LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }



    @PostMapping("/createTransaction")
    public ResponseEntity<StandardResponse<TransactionResponse>> createTransaction(@RequestBody TransactionRequest request) {
        Transaction createdTransaction = transactionService.createTransaction(request);

        TransactionResponse responseData = TransactionResponse.builder()
                .id(createdTransaction.getId())
                .transactionId(createdTransaction.getTransactionId())
                .accountHeadCode(createdTransaction.getAccountHead().getAccountCode())
                .type(createdTransaction.getType())
                .amount(createdTransaction.getAmount())
                .transactionDate(createdTransaction.getTransactionDate())
                .academicYear(createdTransaction.getAcademicYear())
                .description(createdTransaction.getDescription())
                .status(createdTransaction.getStatus())
                .build();

        StandardResponse<TransactionResponse> response = StandardResponse.<TransactionResponse>builder()
                .success(true)
                .message("Transaction created successfully")
                .data(responseData)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

}
