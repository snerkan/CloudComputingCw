package com.pg4.cloudcw.Service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pg4.cloudcw.Dao.FileDao;
import com.pg4.cloudcw.Entity.File;

@Service
public class FileService {

	@Autowired
	private FileDao fileDao;
	
	public Collection<File> getAllFiles() {
		 return fileDao.getAllFiles(); 
	 }  
}
