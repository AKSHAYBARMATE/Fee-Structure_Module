package com.schoolerp.feemodule.repository;

import com.schoolerp.feemodule.entity.CommonMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonMasterRepository extends JpaRepository<CommonMaster, Integer> {
}

