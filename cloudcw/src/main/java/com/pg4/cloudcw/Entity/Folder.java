package com.pg4.cloudcw.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Folder {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private User userId;
	private String address;
	private Folder parentId;
	private Date creationDate; 
	
	@Enumerated(EnumType.ORDINAL)
	private Flag flagId;
	
	public Folder(User userId, Folder parentId, Date creationDate) {
		super();
		this.userId = userId;
		this.parentId = parentId;
		this.creationDate = creationDate;
	}
	
	protected Folder() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Folder getParentId() {
		return parentId;
	}

	public void setParentId(Folder parentId) {
		this.parentId = parentId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
