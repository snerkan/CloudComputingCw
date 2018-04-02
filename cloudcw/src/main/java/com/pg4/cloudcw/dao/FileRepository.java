package com.pg4.cloudcw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pg4.cloudcw.entity.File;
import com.pg4.cloudcw.entity.User;

public interface FileRepository extends CrudRepository<File, Integer> {
	 File findById(int id);
	 List<File> findByUserIdAndIsDeleted(int userId, boolean isDeleted);
}
