package com.schoolerp.feemodule.repository;


import com.schoolerp.feemodule.entity.FeeStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeStructureRepository extends JpaRepository<FeeStructure, Long>, JpaSpecificationExecutor<FeeStructure> {
    FeeStructure findByIdAndIsDeletedFalse(Long id);
}
