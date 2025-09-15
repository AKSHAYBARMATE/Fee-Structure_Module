package com.schoolerp.feemodule.response;

import com.schoolerp.feemodule.entity.FeeStructure;

public class FeeStructureMapper {

    public static FeeStructureResponse toResponse(FeeStructure feeStructure) {
        if (feeStructure == null) return null;

        FeeStructureResponse response = new FeeStructureResponse();

        response.setId(feeStructure.getId());

        // Class Info
        if (feeStructure.getClassId() != null) {
            response.setClassId((long) feeStructure.getClassId().getId());
            response.setClassName(feeStructure.getClassId().getData());
        }

        // Section Info
        if (feeStructure.getSectionId() != null) {
            response.setSectionId((long) feeStructure.getSectionId().getId());
            response.setSectionName(feeStructure.getSectionId().getData());
        }

        // Academic Year
        if (feeStructure.getAcademicYear() != null) {
            response.setAcademicYearId((long) feeStructure.getAcademicYear().getId());
            response.setAcademicYearName(feeStructure.getAcademicYear().getData());
        }

        // Payment Frequency
        if (feeStructure.getPaymentFrequency() != null) {
            response.setPaymentFrequencyId((long) feeStructure.getPaymentFrequency().getId());
            response.setPaymentFrequencyName(feeStructure.getPaymentFrequency().getData());
        }

        // Fees
        response.setFeeStructureName(feeStructure.getFeeStructureName());
        response.setTuitionFee(feeStructure.getTuitionFee());
        response.setAdmissionFee(feeStructure.getAdmissionFee());
        response.setTransportFee(feeStructure.getTransportFee());
        response.setLibraryFee(feeStructure.getLibraryFee());
        response.setExamFee(feeStructure.getExamFee());
        response.setSportsFee(feeStructure.getSportsFee());
        response.setLabFee(feeStructure.getLabFee());
        response.setDevelopmentFee(feeStructure.getDevelopmentFee());
        response.setTotalFee(feeStructure.getTotalFee());
        response.setMaxDiscount(feeStructure.getMaxDiscount());
        response.setLateFeePenalty(feeStructure.getLateFeePenalty());

        // Dates & Meta
        response.setEffectiveFrom(feeStructure.getEffectiveFrom());
        response.setDueDate(feeStructure.getDueDate());
        response.setCreatedAt(feeStructure.getCreatedAt());
        response.setUpdatedAt(feeStructure.getUpdatedAt());
        response.setCreatedBy(feeStructure.getCreatedBy());
        response.setUpdatedBy(feeStructure.getUpdatedBy());
        response.setIsDeleted(feeStructure.getIsDeleted());

        return response;
    }
}
