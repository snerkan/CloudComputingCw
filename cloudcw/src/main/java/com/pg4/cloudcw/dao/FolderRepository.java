package com.pg4.cloudcw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pg4.cloudcw.entity.Folder;

public interface FolderRepository extends JpaRepository<Folder, Integer> {

	Folder findById(int id);

	List<Folder> findByUserIdAndIsDeleted(int userId, boolean isDeleted);
	
	List<Folder> findByUserIdAndParentFolderId(int userId, int parentId);

	List<Folder> findByUserIdAndParentFolderIdAndIsDeleted(int userId, int parentId, boolean isDeleted);
	
	boolean existsByName(String name);

}
