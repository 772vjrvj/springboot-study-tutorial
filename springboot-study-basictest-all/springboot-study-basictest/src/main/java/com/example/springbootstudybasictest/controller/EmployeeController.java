package com.example.springbootstudybasictest.controller;

import com.example.springbootstudybasictest.service.EmployeeService;
import com.example.springbootstudybasictest.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
//@CrossOrigin("http://localhost:3000") //front 에서 proxy 설정을 했으므로 ( * 운영시에는 서버에서 해야함)
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<EmployeeVO> getEmployeeList(){
        List<EmployeeVO> employeeList = employeeService.selectEmployeeList();
        return employeeList;
    }

}
