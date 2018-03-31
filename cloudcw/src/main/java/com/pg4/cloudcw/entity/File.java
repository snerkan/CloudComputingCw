package com.pg4.cloudcw.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

@Entity
public class File {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@ManyToOne
	@JoinColumn(name = "folderId")
	private Folder folder;

	private String address;

	private Date creationDate;
	
	private boolean isDeleted=false;

	// private FileType type;

	// private Flag flagId;

	protected File() {
	}

	public File(User user, String address, Folder folder) {
		super();
		this.user = user;
		this.folder = folder;
		this.address = address;
		// this.creationDate = new Date();
	}

	public File(User user, String address) {
		super();
		this.user = user;
		this.address = address;
		// this.folder = -1;
		// this.creationDate = new Date();
	}
	
	public File(String address) {
		super();
		this.address = address;
		// this.folder = -1;
		// this.creationDate = new Date();
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

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

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
}
