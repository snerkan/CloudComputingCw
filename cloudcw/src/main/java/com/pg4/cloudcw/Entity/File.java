package com.pg4.cloudcw.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class File {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private User userId;
	private Folder folderId;
	private String address;
	private Date creationDate;
//	private FileType type;
//	private Flag flagId;
	
	protected File() {}
	
	public File(User userId, Folder folderId, String address) {
		super();
		this.userId = userId;
		this.folderId = folderId;
		this.address = address;
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

	public Folder getFolderId() {
		return folderId;
	}

	public void setFolderId(Folder folderId) {
		this.folderId = folderId;
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
}
