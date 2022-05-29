package com.example.springbootstudybasictest.mapper;

import java.util.List;

import com.example.springbootstudybasictest.vo.DepartmentVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TestMapper {
    List<DepartmentVO> selectTest();
}

