package com.schoolerp.feemodule.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentFeeStatusDTO {
    private Integer studentId;
    private String admissionNo;
    private String studentName;   // combine first + last name
    private Integer academicYear;
    private String feeStatus;     // Paid, Unpaid, Partial, Overdue
}
