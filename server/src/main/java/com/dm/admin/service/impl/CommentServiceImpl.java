package com.dm.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dm.admin.entity.Comment;
import com.dm.admin.mapper.CommentMapper;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> {
	
    @Autowired
    private CommentMapper commentMapper;
    
    public List<Comment> findByOutid(int outid) {
    	return commentMapper.findByOutid(outid);
    }
}
