package com.example.springbootstudybasictest.mapper;

import com.example.springbootstudybasictest.vo.HREmployeeVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface HRThemeMapper {
    int HRThemeTotCnt(HashMap paramObj);
    List<HREmployeeVO> HRThemeList(HashMap paramObj);
}
