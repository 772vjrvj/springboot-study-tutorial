package com.example.springbootstudybasictest.mapper;

import com.example.springbootstudybasictest.vo.EmployeeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EmployeeMapper {
    public List<EmployeeVO> selectEmployeeList();
}
