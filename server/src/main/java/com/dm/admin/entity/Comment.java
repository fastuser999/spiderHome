package com.dm.admin.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;


@TableName("comment")
@Getter
@Setter
@ToString
public class Comment extends Model<Comment> {

    private static final long serialVersionUID = 2L;

	private int id;
	private int outid;
	private String staff;
	private int start;
	private String comment;
	private String date;
	private String support;
	  
	public Comment() {
	}
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
