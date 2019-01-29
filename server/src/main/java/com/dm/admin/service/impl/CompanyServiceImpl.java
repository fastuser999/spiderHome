package com.dm.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dm.admin.entity.Company;
import com.dm.admin.mapper.CompanyMapper;


@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> {
	
    @Autowired
    private CompanyMapper CompanyMapper;
}
