package com.example.springbootstudybasictest.service;

import com.example.springbootstudybasictest.mapper.EmployeeMapper;
import com.example.springbootstudybasictest.vo.EmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    public EmployeeMapper mapper;

    public List<EmployeeVO> selectEmployeeList() {
        return mapper.selectEmployeeList();
    }
}

