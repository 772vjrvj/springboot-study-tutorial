package com.example.springbootstudybasictest.controller;

import com.example.springbootstudybasictest.service.HREmployeeService;
import com.example.springbootstudybasictest.util.PageObject;
import com.example.springbootstudybasictest.vo.HREmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/hr")
//@CrossOrigin("http://localhost:3000") //front 에서 proxy 설정을 했으므로 ( * 운영시에는 서버에서 해야함)
public class HREmployeeController {

    @Autowired
    private HREmployeeService hrEmployeeService;

    @GetMapping("/employees")
    //public List<HREmployeeVO> getHREmployeeList(@RequestParam(required = true) Integer page, @RequestParam(required = true) Integer perPageRow, @RequestParam(required = true) Integer perGroupPage)
    //public List<HREmployeeVO> getHREmployeeList(@RequestParam Integer page, @RequestParam Integer perPageRow, @RequestParam Integer perGroupPage)
    //public HashMap<String, Object> getHREmployeeList(int page , int perPageRow, int perGroupPage)
    public List<HREmployeeVO> getHREmployeeList(PageObject pageObject)
    {

        //PageObject pageObject = new PageObject(page, perPageRow, perGroupPage);
        List<HREmployeeVO> employeeList = hrEmployeeService.selectHREmployeeList(pageObject);
        return employeeList;
    }
}
