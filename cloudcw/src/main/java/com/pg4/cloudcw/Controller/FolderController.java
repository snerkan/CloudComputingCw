package com.pg4.cloudcw.Controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pg4.cloudcw.Entity.Folder;
import com.pg4.cloudcw.Service.FolderService;

@RestController
@RequestMapping("/")
public class FolderController {

	@Autowired
	private FolderService folderService;
	
	//@RequestMapping(value="/{id}",method=RequestMethod.GET)
	@RequestMapping(method=RequestMethod.GET)
	public Collection<Folder> getAllFoldersByUserId (/*@PathVariable("id")*/  int userId) {
		 return folderService.getAllFoldersByUserId(userId); //TODO Not all 
	 }
	 
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public Collection<Folder> DeleteFolderById (/*@PathVariable("id")*/  int id) {
		// return folderService.getAllFoldersByUserId(userId); //TODO Not all 
		return null;
	 }
	 
}
 