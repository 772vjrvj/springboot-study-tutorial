package com.example.springbootstudybasictest.service;

import com.example.springbootstudybasictest.mapper.HREmployeeMapper;
import com.example.springbootstudybasictest.util.PageObject;
import com.example.springbootstudybasictest.vo.HREmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class HREmployeeService {
    @Autowired
    public HREmployeeMapper mapper;

// 페이징 서버 처리용
//    public HashMap<String, Object> selectHREmployeeList(PageObject pageObject) {
//        HashMap<String, Object> result = new HashMap<>();
//        List<HREmployeeVO> employeeList = mapper.selectHREmployeeList(pageObject);
//        int totCnt = mapper.HREmployeeTotCnt();
//        pageObject.setTotalRow(totCnt);
//        result.put("employeeList", employeeList);
//        result.put("pageObject", pageObject);
//        return result;
//    }

    // 페이징 프론트 처리용
    public List<HREmployeeVO> selectHREmployeeList(PageObject pageObject) {
        List<HREmployeeVO> employeeList = mapper.selectHREmployeeList(pageObject);
        return employeeList;
    }
}

