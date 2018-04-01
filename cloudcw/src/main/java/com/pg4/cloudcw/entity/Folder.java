package com.pg4.cloudcw.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

@Entity
public class Folder {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	private String address;

	private Date creationDate;
	
	private boolean isDeleted=false;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<File> files;
	
	/*	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId")
	private Folder parent;
	
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

	/*public Folder getParent() {
		return parent;
	}

	public void setParent(Folder parent) {
		this.parent = parent;
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
