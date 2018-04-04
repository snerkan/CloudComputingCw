package com.pg4.cloudcw.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;


@Entity
public class File {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true)
	private int id;

	@NotNull
	private String name;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@ManyToOne
	@JoinColumn(name = "folderId")
	private Folder folder;

	@NotNull
	private String storageAddress;

	@NotNull
	//@Size(max = 100)
    @Column()
	private String storageName;
	
	private Date creationDate;
	
	private boolean isDeleted=false;
	
	@Enumerated(EnumType.STRING)
	private Flag flag;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private Set<User> shareList;
	
	protected File() {
	}

	public File(String name, User user, Folder folder, String storageAddress, String storageName) {
		super();
		this.name = name;
		this.user = user;
		this.folder = folder;
		this.storageAddress = storageAddress;
		this.storageName = storageName;
		//this.creationDate = new Date();
		this.isDeleted = false;
	}

	 @PrePersist
	  protected void onCreate() {
		creationDate = new Date();
	  }



	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public User getUser() {
		return user;
	}

	public Folder getFolder() {
		return folder;
	}

	public String getStorageAddress() {
		return storageAddress;
	}

	public String getStorageName() {
		return storageName;
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

	public Set<User> getShareList() {
		return shareList;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public void setStorageAddress(String storageAddress) {
		this.storageAddress = storageAddress;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
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

	public void setShareList(Set<User> shareList) {
		this.shareList = shareList;
	}
}
