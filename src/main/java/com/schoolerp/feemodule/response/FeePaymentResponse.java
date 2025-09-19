package com.schoolerp.feemodule.response;


import com.schoolerp.feemodule.request.FeeCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeePaymentResponse {
    private Long id;
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
    private LocalDateTime paymentDate;
}
