package com.schoolerp.feemodule.mapper;

import com.schoolerp.feemodule.entity.*;
import com.schoolerp.feemodule.request.FeeCategoryDto;
import com.schoolerp.feemodule.request.FeePaymentRequest;
import com.schoolerp.feemodule.response.FeePaymentResponse;
import java.util.List;
import java.util.stream.Collectors;


public class FeePaymentMapper {

    public static FeePaymentResponse toResponse(FeePayment feePayment) {
        FeePaymentResponse response = new FeePaymentResponse();

        // Map FeePayment to FeePaymentResponse
        response.setId(feePayment.getId());
        response.setStudentId(feePayment.getStudentId().getId()); // Get student ID
        response.setFeeStructureId(feePayment.getFeeStructure().getId()); // Get fee structure ID
        response.setBaseAmount(feePayment.getBaseAmount());
        response.setDiscount(feePayment.getDiscount());
        response.setLateFee(feePayment.getLateFee());
        response.setNetAmount(feePayment.getNetAmount());
        response.setPaymentMethod(feePayment.getPaymentMethod());
        response.setTransactionId(feePayment.getTransactionId());
        response.setReceiptNumber(feePayment.getReceiptNo());
        response.setDueDate(feePayment.getDueDate());
        response.setRemarks(feePayment.getPaymentRemarks());
        response.setPaymentDate(feePayment.getPaidAt());

        // Map FeePaymentCategory to FeeCategoryDto
        List<FeeCategoryDto> categoryDtos = feePayment.getSelectedCategories().stream()
                .map(category -> new FeeCategoryDto(category.getCategoryName(), category.getAmount()))
                .collect(Collectors.toList());

        response.setSelectedCategories(categoryDtos);

        return response;
    }
}

