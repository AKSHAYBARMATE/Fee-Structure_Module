package com.schoolerp.feemodule.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FeeStructureRequest {
    private Long classId;
    private Long sectionId;
    private Long academicYearId;
    private String feeStructureName;
    private Long paymentFrequencyId;
    private BigDecimal tuitionFee;
    private BigDecimal admissionFee;
    private BigDecimal transportFee;
    private BigDecimal libraryFee;
    private BigDecimal examFee;
    private BigDecimal sportsFee;
    private BigDecimal labFee;
    private BigDecimal developmentFee;
    private BigDecimal totalFee;
    private BigDecimal maxDiscount;
    private BigDecimal lateFeePenalty;
    private LocalDate effectiveFrom;
    private LocalDate dueDate;
}

