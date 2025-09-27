package com.schoolerp.feemodule.service;

import com.schoolerp.feemodule.entity.*;
import com.schoolerp.feemodule.mapper.FeePaymentMapper;
import com.schoolerp.feemodule.repository.*;
import com.schoolerp.feemodule.request.FeePaymentRequest;
import com.schoolerp.feemodule.response.FeePaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeePaymentServiceImpl implements FeePaymentService {

    @Autowired
    private FeePaymentRepository feePaymentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FeeStructureRepository feeStructureRepository;

    @Autowired
    private AccountHeadRepository accountHeadRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public FeePaymentResponse collectFee(FeePaymentRequest request) {
        // Fetch the student and fee structure
        Student student = studentRepository.findByIdAndIsDeletedFalse(request.getStudentId())
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
                .paidAt(LocalDateTime.now())
                .accountHeadId(request.getAccountHeadId())
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


        // ✅ Create Transaction entry linked to AccountHead
        AccountHead accountHead = accountHeadRepository.findByIdAndIsDeletedFalse(request.getAccountHeadId())
                .orElseThrow(() -> new RuntimeException("AccountHead not found with ID: " + request.getAccountHeadId()));

        Transaction transaction = Transaction.builder()
                .transactionId(request.getTransactionId())
                .accountHead(accountHead)
                .type(Transaction.TransactionType.CREDIT)   // 💡 usually CREDIT for fee collection
                .amount(finalAmount)
                .transactionDate(LocalDate.from(LocalDateTime.now()))
                .academicYear(request.getAcademicYear())
                .description("Fee collected for student: " + student.getFirstName() + " " + student.getLastName())
                .status(Transaction.TransactionStatus.APPROVED) // or from request if flexible
                .isDeleted(false)
                .build();

        this.transactionRepository.save(transaction);

        return FeePaymentMapper.toResponse(savedFeePayment);
    }

}


