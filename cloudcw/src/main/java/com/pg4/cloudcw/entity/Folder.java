package com.pg4.cloudcw.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	private String name;

	private Date creationDate;
	
	private boolean isDeleted=false;
	
	@Enumerated(EnumType.STRING)
	private Flag flag;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<File> files;
	
	private int parentFolderId;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private Set<User> shareList;
	
	protected Folder() {
		super();
	}
	
	public Folder(String name, User user, int parentFolderId) {
		super();
		this.user = user;
		this.name = name;
		//this.creationDate = creationDate;
		this.isDeleted = false;
		this.parentFolderId = parentFolderId;
	}

	@PrePersist
	  protected void onCreate() {
		creationDate = new Date();
	  }
	
	public int getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public String getName() {
		return name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public Flag getFlag() {
		return flag;
	}

	public Set<File> getFiles() {
		return files;
	}

	public int getParentFolderId() {
		return parentFolderId;
	}

	public Set<User> getShareList() {
		return shareList;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setFlag(Flag flag) {
		this.flag = flag;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

	public void setParentFolderId(int parentFolderId) {
		this.parentFolderId = parentFolderId;
	}

	public void setShareList(Set<User> shareList) {
		this.shareList = shareList;
	}
	
}
