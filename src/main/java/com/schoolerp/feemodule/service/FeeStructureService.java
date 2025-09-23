package com.schoolerp.feemodule.service;


import com.schoolerp.feemodule.request.FeeStructureRequest;
import com.schoolerp.feemodule.response.FeeStructureResponse;
import com.schoolerp.feemodule.response.RecentTransactionDTO;
import com.schoolerp.feemodule.response.StudentFeeStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeeStructureService {

    FeeStructureResponse createFeeStructure(FeeStructureRequest request);

    FeeStructureResponse updateFeeStructure(Long id, FeeStructureRequest request);

    FeeStructureResponse getFeeStructureById(Long id);

    void deleteFeeStructure(Long id);

    Page<FeeStructureResponse> getAllFeeStructures(Long classId, Long sectionId, String feeStructureName, Pageable pageable);


    Page<RecentTransactionDTO> getRecentTransactions(Integer academicYear, Pageable pageable);

    Page<StudentFeeStatusDTO> getStudentsFeeStatus(Integer academicYear, Integer classId, Pageable pageable);
}
