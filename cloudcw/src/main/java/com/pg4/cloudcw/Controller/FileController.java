package com.pg4.cloudcw.Controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pg4.cloudcw.Entity.File;
import com.pg4.cloudcw.Service.FileService;

@RestController
@RequestMapping("/")
public class FileController {
	@Autowired
	private FileService fileService;
	
	@RequestMapping(method=RequestMethod.GET)
	public Collection<File> getAllFiles () {
		 return fileService.getAllFiles() ; 
	 }
}
