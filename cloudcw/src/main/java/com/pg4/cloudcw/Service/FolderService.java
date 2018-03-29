package com.pg4.cloudcw.Service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pg4.cloudcw.Dao.FolderDao;
import com.pg4.cloudcw.Entity.Folder;

@Service
public class FolderService {

	@Autowired
	private FolderDao folderDao;
	
	public Collection<Folder> getAllFoldersByUserId (int userId) {
		 return folderDao.getAllFolders(); 
	 }
}
