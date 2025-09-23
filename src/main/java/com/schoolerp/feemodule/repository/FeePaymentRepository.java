package com.schoolerp.feemodule.repository;


import com.schoolerp.feemodule.entity.FeePayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {

    @Query("SELECT f FROM FeePayment f " +
            "JOIN FETCH f.studentId s " +
            "WHERE f.academicYear = :academicYear " +
            "AND (:classId IS NULL OR s.classApplyingFor = :classId) " +
            "ORDER BY f.paidAt DESC")
    List<FeePayment> findPaymentsWithStudentByAcademicYearAndClass(
            @Param("academicYear") Integer academicYear,
            @Param("classId") Integer classId
    );


    Page<FeePayment> findByAcademicYearOrderByPaidAtDesc(Integer academicYear, Pageable pageable);
}
