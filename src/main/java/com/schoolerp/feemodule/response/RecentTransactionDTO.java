package com.schoolerp.feemodule.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecentTransactionDTO {
    private Long paymentId;
    private String studentName;   // combine first + last name
    private BigDecimal netAmount;
    private String paymentMethod;
    private String transactionId;
    private String receiptNo;
    private LocalDateTime paidAt;
}
