package com.example.springbootstudybasictest.service;

import com.example.springbootstudybasictest.mapper.TestMapper;
import com.example.springbootstudybasictest.vo.DepartmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TestService {

    @Autowired
    public TestMapper mapper;

    public List<DepartmentVO> selectTest() {
        return mapper.selectTest();
    }
}

