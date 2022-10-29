package com.example.springbootstudybasictest.util;

public class EmployeeConstant {
    private static final String CLUSTER = "/hr";
    private static final String COMPONENT = "/app-main";

    //base uri
    public static final String BASE_URI = CLUSTER + COMPONENT;

    public static final String EMPLOYEE = BASE_URI + "/employee";

    public static final String THEME = BASE_URI + "/theme";

}
