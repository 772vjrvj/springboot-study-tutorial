package com.example.springbootstudybasictest.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HREmployeeVO {
    private int rowNum;
    private int totalCount;
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String hireDate;
    private String jobId;
    private int salary;
    private int commissionPct;
    private int managerId;
    private int departmentId;
}
