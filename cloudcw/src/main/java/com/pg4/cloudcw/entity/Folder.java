package com.pg4.cloudcw.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

@Entity
public class Folder {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@ManyToOne
    @JoinColumn(name = "userId")
	private User user;
	
	private String address;

	private Date creationDate;
	
	private boolean isDeleted=false;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<File> files;
	

	/*
	private Folder parentId;
	
	@Enumerated(EnumType.ORDINAL)
	private Flag flagId;
	*/

	public Folder(User user, String address) {
		super();
		this.user = user;
		this.address = address;
		//this.creationDate = new Date();
	}
	
	public Folder(User user, String address, Folder folder) {
		super();
		this.user = user;
		this.address = address;
		// this.folder = -1;
		//this.creationDate = new Date();
	}

	protected Folder() {
		super();
	}
	
	@PrePersist
	  protected void onCreate() {
		creationDate = new Date();
	  }
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
/*
	public Folder getParentId() {
		return parentId;
	}

	public void setParentId(Folder parentId) {
		this.parentId = parentId;
	}*/

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

}
