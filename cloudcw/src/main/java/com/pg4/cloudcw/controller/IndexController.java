package com.pg4.cloudcw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pg4.cloudcw.entity.File;
import com.pg4.cloudcw.service.FileService;

@Controller
public class IndexController {

	private FileService fileService;
	
	@GetMapping("/getContent")
	public String getContent() { 
		return "hahah";
		//return fileService.getAll().toString();
	}
	
	/*
	@GetMapping("/")
    public String homePage() {
        return "index";
    }*/
	
	
	public void createFile() {
		File newFile = new File ("0");
		fileService.create(newFile);	
	}
}
