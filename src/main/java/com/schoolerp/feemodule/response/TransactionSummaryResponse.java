package com.schoolerp.feemodule.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class TransactionSummaryResponse {

    private List<TransactionResponse> transactions; // list of transactions
    private Long totalTransactions;                 // total number of transactions
    private BigDecimal totalCredits;                // sum of CREDIT transactions
    private BigDecimal totalDebits;                 // sum of DEBIT transactions
    private BigDecimal netBalance;
    private int pageNumber;
    private int pageSize;
    private long totalPages;
}
