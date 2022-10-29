package com.example.springbootstudybasictest.controller;

import com.example.springbootstudybasictest.service.HRThemeService;
import com.example.springbootstudybasictest.util.EmployeeConstant;
import com.example.springbootstudybasictest.util.PageObject;
import com.example.springbootstudybasictest.vo.HREmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EmployeeConstant.THEME)
public class HRThemeController {

    @Autowired
    private HRThemeService hrThemeService;

    @GetMapping("/list")
    public List<HREmployeeVO> getHRThemeList(PageObject pageObject, String name, String style, String type, String user, String date)
    {

        return null;
    }
}
