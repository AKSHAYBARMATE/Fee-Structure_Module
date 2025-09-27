package com.schoolerp.feemodule.service;


import com.schoolerp.feemodule.entity.AccountHead;
import com.schoolerp.feemodule.exception.CustomException;
import com.schoolerp.feemodule.repository.AccountHeadRepository;
import com.schoolerp.feemodule.response.AccountHeadDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountHeadServiceImpl implements AccountHeadService {

    private final AccountHeadRepository accountHeadRepository;

    @Override
    public AccountHeadDTO createAccountHead(AccountHeadDTO dto) {
        AccountHead accountHead = AccountHead.builder()
                .accountName(dto.getAccountName())
                .accountCode(dto.getAccountCode())
                .accountType(AccountHead.AccountType.valueOf(dto.getAccountType()))
                .description(dto.getDescription())
                .isDeleted(false)
                .build();

        if (dto.getParentAccountId() != null) {
            AccountHead parent = accountHeadRepository.findById(dto.getParentAccountId())
                    .orElseThrow(() -> new RuntimeException("Parent account not found"));
            accountHead.setParentAccount(parent);
        }

        AccountHead saved = accountHeadRepository.save(accountHead);
        return toDTO(saved);
    }



    @Override
    public Page<AccountHeadDTO> getAccountHeadsByType(AccountHead.AccountType accountType, Pageable pageable) {
        Page<AccountHead> page = accountHeadRepository.findAllByIsDeletedFalseAndAccountType(accountType, pageable);
        return page.map(this::toDTO);
    }

    @Override
    public AccountHeadDTO updateAccountHead(Long id, AccountHeadDTO dto) {

        Optional<AccountHead> optional = accountHeadRepository.findByIdAndIsDeletedFalse(id);

        if (optional.isEmpty()) {
            throw new CustomException(
                    "Account head not found",
                    "ACCOUNT_HEAD_NOT_FOUND",
                    "No active account head exists with ID: " + id
            );
        }

        AccountHead existing = optional.get();


        existing.setAccountName(dto.getAccountName());
        existing.setAccountCode(dto.getAccountCode());
        existing.setAccountType(AccountHead.AccountType.valueOf(dto.getAccountType()));
        existing.setDescription(dto.getDescription());
        existing.setIsDeleted(false);

        if (dto.getParentAccountId() != null) {
            AccountHead parent = accountHeadRepository.findById(dto.getParentAccountId())
                    .orElseThrow(() -> new RuntimeException("Parent account not found"));
            existing.setParentAccount(parent);
        } else {
            existing.setParentAccount(null);
        }

        AccountHead updated = accountHeadRepository.save(existing);
        return toDTO(updated);
    }

    @Override
    public void deleteAccountHead(Long id) {
        // Option A: Load and set flag
        AccountHead existing = accountHeadRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new CustomException(
                        "Account head not found",
                        "ACCOUNT_HEAD_NOT_FOUND",
                        "No active account head exists with ID: " + id
                ));

        existing.setIsDeleted(true);
        accountHeadRepository.save(existing);
    }


    @Override
    public AccountHeadDTO getAccountHeadById(Long id) {
        log.info("Fetching AccountHead with ID: {}", id);

        AccountHead accountHead = accountHeadRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("AccountHead not found with ID: {}", id);
                    return new RuntimeException("AccountHead not found with id: " + id);
                });

        log.debug("Fetched AccountHead details: {}", accountHead);
        return toDTO(accountHead);
    }


    private AccountHeadDTO toDTO(AccountHead entity) {
        return AccountHeadDTO.builder()
                .id(entity.getId())
                .accountName(entity.getAccountName())
                .accountCode(entity.getAccountCode())
                .accountType(entity.getAccountType().name())
                .description(entity.getDescription())
                .parentAccountId(entity.getParentAccount() != null ? entity.getParentAccount().getId() : null)
                .build();
    }


}
