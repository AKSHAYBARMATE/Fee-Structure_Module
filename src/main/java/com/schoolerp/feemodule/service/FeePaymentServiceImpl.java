package com.schoolerp.feemodule.service;

import com.schoolerp.feemodule.entity.FeePayment;
import com.schoolerp.feemodule.entity.FeePaymentCategory;
import com.schoolerp.feemodule.entity.FeeStructure;
import com.schoolerp.feemodule.entity.Student;
import com.schoolerp.feemodule.mapper.FeePaymentMapper;
import com.schoolerp.feemodule.repository.FeePaymentRepository;
import com.schoolerp.feemodule.repository.FeeStructureRepository;
import com.schoolerp.feemodule.repository.StudentRepository;
import com.schoolerp.feemodule.request.FeePaymentRequest;
import com.schoolerp.feemodule.response.FeePaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeePaymentServiceImpl implements FeePaymentService {

    @Autowired
    private FeePaymentRepository feePaymentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FeeStructureRepository feeStructureRepository;

    public FeePaymentResponse collectFee(FeePaymentRequest request) {
        // Fetch the student and fee structure
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        FeeStructure feeStructure = feeStructureRepository.findById(request.getFeeStructureId())
                .orElseThrow(() -> new RuntimeException("Fee structure not found"));

        // Handle nulls for BigDecimal values
        BigDecimal totalFee = request.getSelectedCategories().stream()
                .map(dto -> dto.getAmount() != null ? dto.getAmount() : BigDecimal.ZERO) // Fallback to BigDecimal.ZERO if amount is null
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal discount = (request.getDiscount() != null) ? request.getDiscount() : BigDecimal.ZERO;
        BigDecimal lateFee = (request.getLateFee() != null) ? request.getLateFee() : BigDecimal.ZERO;
        BigDecimal netAmount = (request.getNetAmount() != null) ? request.getNetAmount() : BigDecimal.ZERO;

        // Calculate final fee amount considering discount and late fee
        BigDecimal finalAmount = totalFee.subtract(discount).add(lateFee);

        // Create FeePayment entity
        FeePayment feePayment = FeePayment.builder()
                .studentId(student)  // Set the student entity reference
                .feeStructure(feeStructure)
                .baseAmount(totalFee)
                .discount(discount)
                .lateFee(lateFee)
                .academicYear(request.getAcademicYear())
                .netAmount(finalAmount)  // Use the final calculated amount
                .paymentMethod(request.getPaymentMethod())
                .transactionId(request.getTransactionId())
                .receiptNo(request.getReceiptNumber())
                .dueDate(request.getDueDate())
                .paymentRemarks(request.getRemarks())
                .paidAt(LocalDateTime.now())  // Set the current time for payment
                .build();

        // Map selected categories from FeeCategoryDto to FeePaymentCategory
        List<FeePaymentCategory> categories = request.getSelectedCategories().stream()
                .map(dto -> {
                    // Ensure categoryName and amount are not null
                    String categoryName = dto.getCategoryName() != null ? dto.getCategoryName() : "Unknown Category";
                    BigDecimal amount = dto.getAmount() != null ? dto.getAmount() : BigDecimal.ZERO;

                    // Create FeePaymentCategory
                    return FeePaymentCategory.builder()
                            .categoryName(categoryName)
                            .amount(amount)
                            .feePayment(feePayment)  // Set the FeePayment reference
                            .build();
                })
                .collect(Collectors.toList());

        feePayment.setSelectedCategories(categories);  // Set the list of categories in FeePayment

        // Save the fee payment and return a mapped response
        FeePayment savedFeePayment = feePaymentRepository.save(feePayment);

        // Update student fee status
        student.setFeesStatus(Student.FeesStatus.PAID);
        studentRepository.save(student);

        // Map the saved FeePayment to FeePaymentResponse and return it
        return FeePaymentMapper.toResponse(savedFeePayment);
    }

}


