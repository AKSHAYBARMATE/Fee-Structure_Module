package com.schoolerp.feemodule.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountHeadDTO {
    private Long id;
    private String accountName;
    private String accountCode;
    private String accountType; // Could be an enum
    private Long parentAccountId; // Nullable
    private String description;
}
