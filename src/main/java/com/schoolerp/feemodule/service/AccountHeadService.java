package com.schoolerp.feemodule.service;

import com.schoolerp.feemodule.entity.AccountHead;
import com.schoolerp.feemodule.response.AccountHeadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountHeadService {
    AccountHeadDTO createAccountHead(AccountHeadDTO dto);


    Page<AccountHeadDTO> getAccountHeadsByType(AccountHead.AccountType accountType, Pageable pageable);

    AccountHeadDTO updateAccountHead(Long id, AccountHeadDTO dto);

    void deleteAccountHead(Long id);
}
