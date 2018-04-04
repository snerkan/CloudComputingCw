package com.pg4.cloudcw.dao;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pg4.cloudcw.entity.File;
import com.pg4.cloudcw.entity.Folder;

public interface FileRepository extends CrudRepository<File, Integer> {
	
	File findById(int id);
	 
	List<File> findByUserIdAndIsDeleted(int userId, boolean isDeleted);
	 
	List<File> findByUserIdAndFolderIdAndIsDeleted(int userId, int folderId, boolean isDeleted);
		
	 boolean existsByName(String name);
	 
}
