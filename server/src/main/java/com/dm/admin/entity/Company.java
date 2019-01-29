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


@TableName("company_info")
@Getter
@Setter
@ToString
public class Company extends Model<Company> {

    private static final long serialVersionUID = 2L;

	private int id;
	private String name;
	private String industry;
	private String person;
	private String city;
	private String website;
	private String location;
	private String description;
	private String wages;
	private String score;
	private int outid;

	public Company() {
	}
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
