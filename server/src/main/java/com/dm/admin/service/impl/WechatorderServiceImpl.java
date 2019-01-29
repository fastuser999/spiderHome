package com.dm.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dm.admin.entity.Wechatorder;
import com.dm.admin.mapper.WechatorderMapper;


@Service
public class WechatorderServiceImpl extends ServiceImpl<WechatorderMapper, Wechatorder> {
	
    @Autowired
    private WechatorderMapper wechatorderMapper;
}
