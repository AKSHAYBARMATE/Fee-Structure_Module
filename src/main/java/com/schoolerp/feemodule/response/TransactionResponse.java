package com.schoolerp.feemodule.response;


import com.schoolerp.feemodule.entity.Transaction.TransactionStatus;
import com.schoolerp.feemodule.entity.Transaction.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private Long id;
    private String transactionId;
    private String accountHeadName;
    private String accountHeadCode;
    private TransactionType type;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private Integer academicYear;
    private String description;
    private TransactionStatus status;
}
