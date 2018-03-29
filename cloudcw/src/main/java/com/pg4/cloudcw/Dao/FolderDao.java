package com.pg4.cloudcw.Dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.pg4.cloudcw.Entity.Folder;

@Repository
public class FolderDao  {
//public interface CustomerRepository extends CrudRepository<Customer, Long> {
	//<folder id,folder>
	private static Map<Integer,Folder>folders;
	
	 public Collection<Folder> getAllFolders () {
		 return folders.values(); 
	 }
}
