package com.example.springbootstudybasictest.vo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class EmployeeVO {
    private long eno;
    private String ename;
    private String job;
    private String manager;
    private String hiredate;
    private int salary;
    private String commission;
    private String dno;
}
