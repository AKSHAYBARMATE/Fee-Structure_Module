package com.schoolerp.feemodule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_head_id", nullable = false)
    private AccountHead accountHead;  // 🔗 Relation to AccountHead

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private TransactionType type; // CREDIT, DEBIT

    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate transactionDate;

    private Integer academicYear;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private TransactionStatus status; // APPROVED, PENDING, REJECTED

    private Boolean isDeleted;



    public enum TransactionType {
        CREDIT, // Money In
        DEBIT   // Money Out
    }

    public enum TransactionStatus {
        APPROVED,
        PENDING,
        REJECTED
    }




}
