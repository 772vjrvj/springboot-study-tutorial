package com.example.springbootstudybasictest.controller;

import com.example.springbootstudybasictest.service.HRTeamService;
import com.example.springbootstudybasictest.vo.HRTeamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/team")
public class HRTeamController {

    @Autowired
    public HRTeamService hrTeamService;

    @GetMapping("/list")
    public List<HRTeamVO> HRTeamList(){
        List<HRTeamVO> hrTeamList = hrTeamService.HRTeamList();return hrTeamList;
    }

}
