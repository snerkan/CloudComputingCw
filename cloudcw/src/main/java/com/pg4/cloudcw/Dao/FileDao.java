package com.pg4.cloudcw.Dao;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pg4.cloudcw.Entity.File;
import com.pg4.cloudcw.Entity.Folder;

@Repository
public class FileDao  {
	private static Map<Integer,File>files;
	
	public Collection<File> getAllFiles() {
		 return files.values(); 
	 }  
}