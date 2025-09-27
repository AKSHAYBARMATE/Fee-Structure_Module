package com.schoolerp.feemodule.controller;


import com.schoolerp.feemodule.entity.AccountHead;
import com.schoolerp.feemodule.response.AccountHeadDTO;
import com.schoolerp.feemodule.response.StandardResponse;
import com.schoolerp.feemodule.service.AccountHeadService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/fee-structures-module")
@RequiredArgsConstructor
public class AccountHeadController {

    private final AccountHeadService accountHeadService;

    @PostMapping("/createAccountHead")
    public ResponseEntity<StandardResponse<AccountHeadDTO>> createAccountHead(@RequestBody AccountHeadDTO dto) {
        AccountHeadDTO created = accountHeadService.createAccountHead(dto);

        StandardResponse<AccountHeadDTO> response = StandardResponse.<AccountHeadDTO>builder()
                .success(true)
                .message("Account head created successfully")
                .data(created)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAccountHeadsByType")
    public ResponseEntity<StandardResponse<Page<AccountHeadDTO>>> getAccountHeadsByType(
            @RequestParam AccountHead.AccountType accountType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<AccountHeadDTO> resultPage = accountHeadService.getAccountHeadsByType(accountType, pageable);

        StandardResponse.ResponseMetadata metadata = StandardResponse.ResponseMetadata.builder()
                .totalRecords(resultPage.getTotalElements())
                .currentPage(resultPage.getNumber())
                .pageSize(resultPage.getSize())
                .totalPages(resultPage.getTotalPages())
                .build();

        StandardResponse<Page<AccountHeadDTO>> response = StandardResponse.<Page<AccountHeadDTO>>builder()
                .success(true)
                .message("Account heads fetched successfully")
                .data(resultPage)
                .metadata(metadata)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("getAccountById/{id}")
    public ResponseEntity<StandardResponse<AccountHeadDTO>> updateAccountHead(
            @PathVariable Long id,
            @RequestBody AccountHeadDTO dto
    ) {
        AccountHeadDTO updated = accountHeadService.updateAccountHead(id, dto);

        StandardResponse<AccountHeadDTO> response = StandardResponse.<AccountHeadDTO>builder()
                .success(true)
                .message("Account head updated successfully")
                .data(updated)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("deleteAccountHead/{id}")
    public ResponseEntity<StandardResponse<Void>> deleteAccountHead(@PathVariable Long id) {
        accountHeadService.deleteAccountHead(id);

        StandardResponse<Void> response = StandardResponse.<Void>builder()
                .success(true)
                .message("Account head deleted successfully")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }
}

