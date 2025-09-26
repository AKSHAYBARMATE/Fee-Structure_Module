package com.schoolerp.feemodule.request;


import com.schoolerp.feemodule.entity.Transaction.TransactionStatus;
import com.schoolerp.feemodule.entity.Transaction.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequest {
    private String transactionId;
    private Long accountHeadId; // reference to AccountHead
    private TransactionType type;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private Integer academicYear;
    private String description;
    private TransactionStatus status;
}
