package com.example.springbootstudybasictest.service;

import com.example.springbootstudybasictest.mapper.HREmployeeMapper;
import com.example.springbootstudybasictest.util.PageObject;
import com.example.springbootstudybasictest.vo.HREmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
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
    public List<HREmployeeVO> HREmployeeList(PageObject pageObject, String key, String value, String commissionNo, String managerNo, String departmentNo, String sorting, List<String> selectDepCol) {
        HashMap<String, Object> paramObj = new HashMap<>();
        paramObj.put("pageObject", pageObject);
        paramObj.put("key", key);
        paramObj.put("value", value);
        paramObj.put("commissionNo", commissionNo);
        paramObj.put("managerNo", managerNo);
        paramObj.put("departmentNo", departmentNo);
        paramObj.put("sorting", sorting);
        paramObj.put("selectDepCol", selectDepCol);
        int totalCount = mapper.HREmployeeTotCnt(paramObj);
        List<HREmployeeVO> employeeList = mapper.HREmployeeList(paramObj);
        if(!CollectionUtils.isEmpty(employeeList)){
            employeeList.get(0).setTotalCount(totalCount);
        }
        return employeeList;
    }

    // 다중 검색
    public List<HREmployeeVO> HREmployeeMultiSearchList(PageObject pageObject, HREmployeeVO hrEmployeeVO) {
        HashMap<String, Object> paramObj = new HashMap<>();
        paramObj.put("pageObject", pageObject);
        paramObj.put("hrEmployeeVO", hrEmployeeVO);
        int totalCount = mapper.HREmployeeMultiSearchTotCnt(paramObj);
        List<HREmployeeVO> employeeList = mapper.HREmployeeMultiSearchList(paramObj);
        if(!CollectionUtils.isEmpty(employeeList)){
            employeeList.get(0).setTotalCount(totalCount);
        }
        return employeeList;
    }

    public List<HashMap<String, String>> HREmployeeColumnList() {
        List<HashMap<String, String>> HREmpColList = mapper.HREmployeeColumnList();
        return HREmpColList;
    }

    public List<HashMap<String, String>> HREmployeeDepartmentList() {
        List<HashMap<String, String>> HREmpDepList = mapper.HREmployeeDepartmentList();
        return HREmpDepList;
    }

    public int HREmployeeDelete(List<String> ids) {
        return mapper.HREmployeeDelete(ids);
    }
}

