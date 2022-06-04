package com.example.springbootstudybasictest.mapper;

import com.example.springbootstudybasictest.util.PageObject;
import com.example.springbootstudybasictest.vo.HREmployeeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface HREmployeeMapper {
    List<HREmployeeVO> selectHREmployeeList(PageObject pageObject);
    int HREmployeeTotCnt();
}
