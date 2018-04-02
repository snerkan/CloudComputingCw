package com.pg4.cloudcw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pg4.cloudcw.entity.Folder;

public interface FolderRepository extends CrudRepository<Folder, Integer> {

	 List<Folder> findByUserIdAndIsDeleted(int userId, boolean isDeleted);
}
