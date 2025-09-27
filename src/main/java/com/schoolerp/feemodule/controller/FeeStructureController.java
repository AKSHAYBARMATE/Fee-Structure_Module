package com.schoolerp.feemodule.controller;


import com.schoolerp.feemodule.entity.FeeStructure;
import com.schoolerp.feemodule.request.FeeStructureRequest;
import com.schoolerp.feemodule.response.FeeStructureResponse;
import com.schoolerp.feemodule.response.RecentTransactionDTO;
import com.schoolerp.feemodule.response.StandardResponse;
import com.schoolerp.feemodule.response.StudentFeeStatusDTO;
import com.schoolerp.feemodule.service.FeeStructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fee-structures-module")
@RequiredArgsConstructor
public class FeeStructureController {

    private final FeeStructureService feeStructureService;

    @PostMapping("createFeeStructure")
    public ResponseEntity<StandardResponse<FeeStructureResponse>> createFeeStructure(@RequestBody FeeStructureRequest request) {
        FeeStructureResponse created = feeStructureService.createFeeStructure(request);
        StandardResponse<FeeStructureResponse> response = StandardResponse.<FeeStructureResponse>builder()
                .success(true)
                .message("Fee structure created successfully")
                .data(created)
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("updateFeeStructure/{id}")
    public ResponseEntity<StandardResponse<FeeStructureResponse>> updateFeeStructure(@PathVariable Long id, @RequestBody FeeStructureRequest request) {
        FeeStructureResponse updated = feeStructureService.updateFeeStructure(id, request);
        StandardResponse<FeeStructureResponse> response = StandardResponse.<FeeStructureResponse>builder()
                .success(true)
                .message("Fee structure updated successfully")
                .data(updated)
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("getFeeStructureById/{id}")
    public ResponseEntity<StandardResponse<FeeStructureResponse>> getFeeStructureById(@PathVariable Long id) {
        FeeStructureResponse found = feeStructureService.getFeeStructureById(id);
        StandardResponse<FeeStructureResponse> response = StandardResponse.<FeeStructureResponse>builder()
                .success(true)
                .message("Fee structure fetched successfully")
                .data(found)
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllFeeStructures")
    public ResponseEntity<StandardResponse<Page<FeeStructureResponse>>> getAllFeeStructures(
            @RequestParam(value = "classId", required = false) Long classId,
            @RequestParam(value = "sectionId", required = false) Long sectionId,
            @RequestParam(value = "feeStructureName", required = false) String feeStructureName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FeeStructureResponse> resultPage = feeStructureService.getAllFeeStructures(classId, sectionId, feeStructureName, pageable);
        StandardResponse.ResponseMetadata metadata = StandardResponse.ResponseMetadata.builder()
                .totalRecords(resultPage.getTotalElements())
                .currentPage(resultPage.getNumber())
                .pageSize(resultPage.getSize())
                .totalPages(resultPage.getTotalPages())
                .build();
        StandardResponse<Page<FeeStructureResponse>> response = StandardResponse.<Page<FeeStructureResponse>>builder()
                .success(true)
                .message("Filtered fee structures fetched successfully")
                .data(resultPage)
                .metadata(metadata)
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("deleteFeeStructure/{id}")
    public ResponseEntity<StandardResponse<Void>> deleteFeeStructure(@PathVariable Long id) {
        feeStructureService.deleteFeeStructure(id);
        StandardResponse<Void> response = StandardResponse.<Void>builder()
                .success(true)
                .message("Fee structure deleted successfully")
                .timestamp(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/studentsFeeStatus")
    public ResponseEntity<StandardResponse<Page<StudentFeeStatusDTO>>> getStudentsFeeStatus(
            @RequestParam(required = false) Integer academicYear,
            @RequestParam(value = "classId", required = false) Integer classId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentFeeStatusDTO> resultPage = feeStructureService.getStudentsFeeStatus(academicYear, classId, pageable);

        StandardResponse.ResponseMetadata metadata = StandardResponse.ResponseMetadata.builder()
                .totalRecords(resultPage.getTotalElements())
                .currentPage(resultPage.getNumber())
                .pageSize(resultPage.getSize())
                .totalPages(resultPage.getTotalPages())
                .build();

        StandardResponse<Page<StudentFeeStatusDTO>> response = StandardResponse.<Page<StudentFeeStatusDTO>>builder()
                .success(true)
                .message("Students fee status fetched successfully")
                .data(resultPage)
                .metadata(metadata)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }



    @GetMapping("/recent-transactions")
    public ResponseEntity<StandardResponse<Page<RecentTransactionDTO>>> getRecentTransactions(
            @RequestParam Integer academicYear,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RecentTransactionDTO> resultPage = feeStructureService.getRecentTransactions(academicYear, pageable);

        StandardResponse.ResponseMetadata metadata = StandardResponse.ResponseMetadata.builder()
                .totalRecords(resultPage.getTotalElements())
                .currentPage(resultPage.getNumber())
                .pageSize(resultPage.getSize())
                .totalPages(resultPage.getTotalPages())
                .build();

        StandardResponse<Page<RecentTransactionDTO>> response = StandardResponse.<Page<RecentTransactionDTO>>builder()
                .success(true)
                .message("Recent transactions fetched successfully")
                .data(resultPage)
                .metadata(metadata)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }



}
