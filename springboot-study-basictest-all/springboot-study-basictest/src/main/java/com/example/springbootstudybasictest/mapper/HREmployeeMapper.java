package com.example.springbootstudybasictest.mapper;

import com.example.springbootstudybasictest.util.PageObject;
import com.example.springbootstudybasictest.vo.HREmployeeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface HREmployeeMapper {
    int HREmployeeTotCnt(HashMap paramObj);
    List<HREmployeeVO> HREmployeeList(HashMap paramObj);

    int HREmployeeMultiSearchTotCnt(HashMap paramObj);
    List<HREmployeeVO> HREmployeeMultiSearchList(HashMap paramObj);

    List<HashMap<String, String>> HREmployeeColumnList();
    List<HashMap<String, String>> HREmployeeDepartmentList();
    int HREmployeeDelete(List<String> ids);

}
