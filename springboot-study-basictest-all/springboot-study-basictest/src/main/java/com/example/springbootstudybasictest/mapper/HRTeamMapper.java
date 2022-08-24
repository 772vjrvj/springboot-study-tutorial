package com.example.springbootstudybasictest.mapper;

import com.example.springbootstudybasictest.vo.HRTeamVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface HRTeamMapper {

    public List<HRTeamVO> HRTeamList();
}
