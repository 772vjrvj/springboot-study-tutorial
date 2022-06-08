package com.example.springbootstudybasictest.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HREmployeeVO {
    private int rowNum = 0;
    private int totalCount = 0;
    private String employeeId = "";
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String phoneNumber = "";
    private String hireDate = "";
    private String jobId = "";
    private int salary = -1; //
    private float commissionPct = -1.0F; //
    private String managerId  = ""; //
    private String departmentId = ""; //
    private String departmentName = "";
    private String managerFirstName = "";
    private String managerLastName = "";
}
