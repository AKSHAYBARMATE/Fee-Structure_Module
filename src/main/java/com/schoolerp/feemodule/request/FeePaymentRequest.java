package com.schoolerp.feemodule.request;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class FeePaymentRequest {

    private Integer studentId;
    private Long feeStructureId;

    private List<FeeCategoryDto> selectedCategories;

    private BigDecimal baseAmount;
    private BigDecimal discount;
    private BigDecimal lateFee;
    private BigDecimal netAmount;

    private String paymentMethod;
    private String transactionId;
    private String receiptNumber;
    private LocalDate dueDate;
    private String remarks;
}
