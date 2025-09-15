package com.schoolerp.feemodule.service;


import com.schoolerp.feemodule.entity.FeeStructure;
import com.schoolerp.feemodule.request.FeeStructureRequest;
import com.schoolerp.feemodule.response.FeeStructureResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeeStructureService {

    FeeStructureResponse createFeeStructure(FeeStructureRequest request);

    FeeStructureResponse updateFeeStructure(Long id, FeeStructureRequest request);

    FeeStructureResponse getFeeStructureById(Long id);

    void deleteFeeStructure(Long id);

    Page<FeeStructureResponse> getAllFeeStructures(Long classId, Long sectionId, String feeStructureName, Pageable pageable);
}
