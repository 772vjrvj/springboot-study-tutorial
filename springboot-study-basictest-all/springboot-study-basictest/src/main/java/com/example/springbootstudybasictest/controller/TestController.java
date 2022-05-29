package com.example.springbootstudybasictest.controller;

import com.example.springbootstudybasictest.service.TestService;
import com.example.springbootstudybasictest.vo.DepartmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping(value = "/home")
    public String home() {
        return "index.html";
    }

    @ResponseBody
    @RequestMapping("/valueTest")
    public String valueTest() {
        String value = "테스트 String";
        return value;
    }

    @RequestMapping("/thymeleafTest")
    public String thymeleafTest(Model model) {
        DepartmentVO department = new DepartmentVO(1, "ACCOUNTING", "NEW YORK");
        model.addAttribute("department", department);
        return "thymeleaf/thymeleafTest";
    }

    @RequestMapping(value = "/mapperTest")
    public ModelAndView test() throws Exception {
        // 화면 경로 thymeleaf/mapperTest
        ModelAndView mav = new ModelAndView("thymeleaf/mapperTest");

        //DB 데이터 조회
        List<DepartmentVO> departmentList = testService.selectTest();

        //화면에서 사용할 VOList 와 이름
        mav.addObject("list", departmentList);

        return mav;
    }

}

