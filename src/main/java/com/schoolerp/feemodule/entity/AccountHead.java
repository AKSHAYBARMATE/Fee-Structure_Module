package com.schoolerp.feemodule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account_heads")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountHead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    @Column(name = "account_code", nullable = false, unique = true)
    private String accountCode;

    private String accountType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_account_id")
    private AccountHead parentAccount;

    @Column(name = "description")
    private String description;

    private Boolean isDeleted;

}
