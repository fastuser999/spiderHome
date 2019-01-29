package com.dm.admin.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dm.admin.entity.Comment;


public interface CommentMapper extends BaseMapper<Comment> {
	public List<Comment> findByOutid(int outid) ;
}