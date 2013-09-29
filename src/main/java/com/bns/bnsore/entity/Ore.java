package com.bns.bnsore.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//JPA标识
@Entity
@Table(name = "bns_ore")
public class Ore extends IdEntity {
	private String name;
	private String types;
	private String map;
	private String line;
	private Date refurbishDate;
	private Date createDate;
	private Date modifyDate;
	private User user;
	
	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

 

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public Date getRefurbishDate() {
		return refurbishDate;
	}

	public void setRefurbishDate(Date refurbishDate) {
		this.refurbishDate = refurbishDate;
	}
 
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	// JPA 基于USER_ID列的多对一关系定义
	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
