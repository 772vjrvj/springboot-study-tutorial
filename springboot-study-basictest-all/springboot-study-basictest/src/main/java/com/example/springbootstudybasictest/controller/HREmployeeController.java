package com.example.springbootstudybasictest.controller;

import com.example.springbootstudybasictest.service.HREmployeeService;
import com.example.springbootstudybasictest.util.PageObject;
import com.example.springbootstudybasictest.vo.HREmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public List<HREmployeeVO> getHREmployeeList(PageObject pageObject, String key, String value, String commissionNo, String managerNo, String departmentNo, String sorting, @RequestParam(value ="selectDepCol[]", required=false) List<String> selectDepCol)
    {
        List<HREmployeeVO> employeeList = hrEmployeeService.HREmployeeList(pageObject, key, value, commissionNo, managerNo, departmentNo, sorting, selectDepCol);
        return employeeList;
    }

    //다중 검색
    @GetMapping("/multiSearch/employees")
    public List<HREmployeeVO> getHRMultiSearchEmployeeList(PageObject pageObject, HREmployeeVO hrEmployeeVO, String commissionNo, String managerNo, String departmentNo, String sorting, @RequestParam(value ="selectDepCol[]", required=false) List<String> selectDepCol)
    {
        List<HREmployeeVO> employeeList = hrEmployeeService.HREmployeeMultiSearchList(pageObject, hrEmployeeVO);
        return employeeList;
    }
    
    @GetMapping("/empColList")
    public List<HashMap<String, String>> getHREmployeeColumnList()
    {
        List<HashMap<String, String>> empColList = hrEmployeeService.HREmployeeColumnList();
        System.out.println("empColList "+ empColList);
        return empColList;
    }

    @GetMapping("/empDepList")
    public List<HashMap<String, String>> getHREmployeeDepartmentList()
    {
        List<HashMap<String, String>> empDepList = null;
        try{
            empDepList = hrEmployeeService.HREmployeeDepartmentList();
            System.out.println("empDepList "+ empDepList);
        }catch(Exception e){
            System.out.println("empColList "+ e.getStackTrace());
        }
        return empDepList;
    }

    @DeleteMapping("/empDelete")
    public int HREmployeeDelete(@RequestParam(value ="ids[]", required=false) List<String> ids){
        return hrEmployeeService.HREmployeeDelete(ids);
    }

}
