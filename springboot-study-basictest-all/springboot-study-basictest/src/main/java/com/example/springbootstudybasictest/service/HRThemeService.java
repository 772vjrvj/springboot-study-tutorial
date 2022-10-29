package com.example.springbootstudybasictest.service;

import com.example.springbootstudybasictest.mapper.HRThemeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HRThemeService {
    @Autowired
    public HRThemeMapper mapper;


}

