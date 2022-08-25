package com.example.springbootstudybasictest.service;

import com.example.springbootstudybasictest.mapper.HRTeamMapper;
import com.example.springbootstudybasictest.vo.HRTeamMemberVO;
import com.example.springbootstudybasictest.vo.HRTeamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HRTeamService {

    @Autowired
    public HRTeamMapper mapper;

    public List<HRTeamVO> HRTeamList(){
        List<HRTeamVO> hrTeamList = mapper.HRTeamList();
        return hrTeamList;
    }

    public List<HRTeamMemberVO> HRTeamMemberList(){
        List<HRTeamMemberVO> hrTeamMemberList = mapper.HRTeamMemberList();
        return hrTeamMemberList;
    }

}
