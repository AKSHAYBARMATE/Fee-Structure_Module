package com.schoolerp.feemodule.service;

import com.schoolerp.feemodule.entity.FeeStructure;
import com.schoolerp.feemodule.repository.FeeStructureRepository;
import com.schoolerp.feemodule.request.FeeStructureRequest;
import com.schoolerp.feemodule.repository.CommonMasterRepository;
import com.schoolerp.feemodule.response.FeeStructureMapper;
import com.schoolerp.feemodule.response.FeeStructureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Service
@RequiredArgsConstructor
public class FeeStructureServiceImpl implements FeeStructureService {

    private static final Logger logger = LoggerFactory.getLogger(FeeStructureServiceImpl.class);
    private final FeeStructureRepository feeStructureRepository;
    private final CommonMasterRepository commonMasterRepository;

    @Override
    public FeeStructureResponse createFeeStructure(FeeStructureRequest request) {
        logger.info("Creating new FeeStructure from request: {}", request);
        FeeStructure feeStructure = new FeeStructure();
        // Fetch related CommonMaster entities
        feeStructure.setClassId(commonMasterRepository.findById(request.getClassId().intValue())
                .orElseThrow(() -> new RuntimeException("Class not found with id " + request.getClassId())));
        feeStructure.setSectionId(commonMasterRepository.findById(request.getSectionId().intValue())
                .orElseThrow(() -> new RuntimeException("Section not found with id " + request.getSectionId())));
        feeStructure.setAcademicYear(commonMasterRepository.findById(request.getAcademicYearId().intValue())
                .orElseThrow(() -> new RuntimeException("Academic Year not found with id " + request.getAcademicYearId())));
        feeStructure.setPaymentFrequency(commonMasterRepository.findById(request.getPaymentFrequencyId().intValue())
                .orElseThrow(() -> new RuntimeException("Payment Frequency not found with id " + request.getPaymentFrequencyId())));
        // Set simple fields
        feeStructure.setFeeStructureName(request.getFeeStructureName());
        feeStructure.setTuitionFee(request.getTuitionFee());
        feeStructure.setAdmissionFee(request.getAdmissionFee());
        feeStructure.setTransportFee(request.getTransportFee());
        feeStructure.setLibraryFee(request.getLibraryFee());
        feeStructure.setExamFee(request.getExamFee());
        feeStructure.setSportsFee(request.getSportsFee());
        feeStructure.setLabFee(request.getLabFee());
        feeStructure.setDevelopmentFee(request.getDevelopmentFee());
        feeStructure.setMaxDiscount(request.getMaxDiscount());
        feeStructure.setLateFeePenalty(request.getLateFeePenalty());
        feeStructure.setEffectiveFrom(request.getEffectiveFrom());
        feeStructure.setDueDate(request.getDueDate());
        feeStructure.setCreatedAt(LocalDate.now());
        feeStructure.setUpdatedAt(null);
        feeStructure.setUpdatedBy(null);
        feeStructure.setIsDeleted(false);
        // Calculate totalFee if not provided
        if (request.getTotalFee() == null) {
            java.math.BigDecimal total = java.math.BigDecimal.ZERO;
            if (request.getTuitionFee() != null) total = total.add(request.getTuitionFee());
            if (request.getAdmissionFee() != null) total = total.add(request.getAdmissionFee());
            if (request.getTransportFee() != null) total = total.add(request.getTransportFee());
            if (request.getLibraryFee() != null) total = total.add(request.getLibraryFee());
            if (request.getExamFee() != null) total = total.add(request.getExamFee());
            if (request.getSportsFee() != null) total = total.add(request.getSportsFee());
            if (request.getLabFee() != null) total = total.add(request.getLabFee());
            if (request.getDevelopmentFee() != null) total = total.add(request.getDevelopmentFee());
            feeStructure.setTotalFee(total);
            logger.info("Calculated totalFee: {}", total);
        } else {
            feeStructure.setTotalFee(request.getTotalFee());
        }
        FeeStructure saved = feeStructureRepository.save(feeStructure);
        logger.info("FeeStructure created with ID: {}", saved.getId());
        return FeeStructureMapper.toResponse(saved);
    }

    @Override
    public FeeStructureResponse updateFeeStructure(Long id, FeeStructureRequest request) {
        logger.info("Updating FeeStructure with ID: {}", id);
        FeeStructure existing = feeStructureRepository.findByIdAndIsDeletedFalse(id);
        if (existing == null) {
            throw new RuntimeException("Fee Structure not found with id " + id);
        }
        // Update related CommonMaster entities if provided
        if (request.getClassId() != null) {
            existing.setClassId(commonMasterRepository.findById(request.getClassId().intValue())
                .orElseThrow(() -> new RuntimeException("Class not found with id " + request.getClassId())));
        }
        if (request.getSectionId() != null) {
            existing.setSectionId(commonMasterRepository.findById(request.getSectionId().intValue())
                .orElseThrow(() -> new RuntimeException("Section not found with id " + request.getSectionId())));
        }
        if (request.getAcademicYearId() != null) {
            existing.setAcademicYear(commonMasterRepository.findById(request.getAcademicYearId().intValue())
                .orElseThrow(() -> new RuntimeException("Academic Year not found with id " + request.getAcademicYearId())));
        }
        if (request.getPaymentFrequencyId() != null) {
            existing.setPaymentFrequency(commonMasterRepository.findById(request.getPaymentFrequencyId().intValue())
                .orElseThrow(() -> new RuntimeException("Payment Frequency not found with id " + request.getPaymentFrequencyId())));
        }
        // Update simple fields
        if (request.getFeeStructureName() != null) existing.setFeeStructureName(request.getFeeStructureName());
        if (request.getTuitionFee() != null) existing.setTuitionFee(request.getTuitionFee());
        if (request.getAdmissionFee() != null) existing.setAdmissionFee(request.getAdmissionFee());
        if (request.getTransportFee() != null) existing.setTransportFee(request.getTransportFee());
        if (request.getLibraryFee() != null) existing.setLibraryFee(request.getLibraryFee());
        if (request.getExamFee() != null) existing.setExamFee(request.getExamFee());
        if (request.getSportsFee() != null) existing.setSportsFee(request.getSportsFee());
        if (request.getLabFee() != null) existing.setLabFee(request.getLabFee());
        if (request.getDevelopmentFee() != null) existing.setDevelopmentFee(request.getDevelopmentFee());
        if (request.getMaxDiscount() != null) existing.setMaxDiscount(request.getMaxDiscount());
        if (request.getLateFeePenalty() != null) existing.setLateFeePenalty(request.getLateFeePenalty());
        if (request.getEffectiveFrom() != null) existing.setEffectiveFrom(request.getEffectiveFrom());
        if (request.getDueDate() != null) existing.setDueDate(request.getDueDate());
        // Optionally recalculate totalFee if not provided
        if (request.getTotalFee() == null) {
            java.math.BigDecimal total = java.math.BigDecimal.ZERO;
            if (existing.getTuitionFee() != null) total = total.add(existing.getTuitionFee());
            if (existing.getAdmissionFee() != null) total = total.add(existing.getAdmissionFee());
            if (existing.getTransportFee() != null) total = total.add(existing.getTransportFee());
            if (existing.getLibraryFee() != null) total = total.add(existing.getLibraryFee());
            if (existing.getExamFee() != null) total = total.add(existing.getExamFee());
            if (existing.getSportsFee() != null) total = total.add(existing.getSportsFee());
            if (existing.getLabFee() != null) total = total.add(existing.getLabFee());
            if (existing.getDevelopmentFee() != null) total = total.add(existing.getDevelopmentFee());
            existing.setTotalFee(total);
            logger.info("Recalculated totalFee: {}", total);
        } else {
            existing.setTotalFee(request.getTotalFee());
        }
        existing.setUpdatedAt(LocalDate.now());
        FeeStructure updated = feeStructureRepository.save(existing);
        logger.info("FeeStructure updated with ID: {}", updated.getId());
        return  FeeStructureMapper.toResponse(updated);
    }

    @Override
    public FeeStructureResponse getFeeStructureById(Long id) {
        logger.info("Fetching FeeStructure by ID: {}", id);
        FeeStructure feeStructure = feeStructureRepository.findByIdAndIsDeletedFalse(id);
        if (feeStructure == null) {
            throw new RuntimeException("Fee Structure not found with id " + id);
        }
        logger.info("Found FeeStructure: {}", feeStructure);
        return FeeStructureMapper.toResponse(feeStructure);
    }


    @Override
    public Page<FeeStructureResponse> getAllFeeStructures(Long classId, Long sectionId, String feeStructureName, Pageable pageable) {
        Specification<FeeStructure> spec = (root, query, cb) -> cb.isFalse(root.get("isDeleted"));

        if (classId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("classId").get("id"), classId.intValue()));
        }

        if (sectionId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("sectionId").get("id"), sectionId.intValue()));
        }

        if (feeStructureName != null && !feeStructureName.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("feeStructureName")), "%" + feeStructureName.toLowerCase() + "%"));
        }

        Page<FeeStructure> entityPage = feeStructureRepository.findAll(spec, pageable);

        return entityPage.map(FeeStructureMapper::toResponse);
    }

    @Override
    public void deleteFeeStructure(Long id) {
        logger.info("Soft deleting FeeStructure with ID: {}", id);
        FeeStructure feeStructure = feeStructureRepository.findByIdAndIsDeletedFalse(id);
        if (feeStructure == null) {
            throw new RuntimeException("Fee Structure not found with id " + id);
        }
        feeStructure.setIsDeleted(true);
        feeStructure.setUpdatedAt(LocalDate.now());
        feeStructureRepository.save(feeStructure);
        logger.info("Soft deleted FeeStructure with ID: {}", id);
    }
}
