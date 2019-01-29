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


@TableName("wechatorder")
@Getter
@Setter
@ToString
public class Wechatorder extends Model<Wechatorder> {

    private static final long serialVersionUID = 3L;

	private String id;
	private String nonce;
	private int uid;
	private Date create_date;
	private Date pay_date;
	private int status;

	public Wechatorder() {
	}
	
	public Wechatorder(String id, 
			String nonce, 
			int uid,
			Date create_date,
			Date pay_date,
			int status) {
		this.id = id;
		this.nonce = nonce;
		this.uid = uid;
		this.create_date = create_date;
		this.pay_date = pay_date;
		this.status = status;
	}
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
