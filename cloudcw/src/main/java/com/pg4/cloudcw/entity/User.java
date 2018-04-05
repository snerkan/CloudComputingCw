package com.pg4.cloudcw.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true)
	private int id;
		
	@NotNull
	@Column(unique = true)
	private String email;
	
	private Date creationDate;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<File> files;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Folder> folders;
	
	@ManyToMany(mappedBy = "user")
	 private Set<File> filesOfOtherUsers;
	
	@ManyToMany(mappedBy = "user")
	 private Set<Folder> foldersOfOtherUsers;
	
	public User() {
		super();
	}

	public User(String email) {
		super();
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

	public Set<Folder> getFolders() {
		return folders;
	}

	public void setFolders(Set<Folder> folders) {
		this.folders = folders;
	}
}
