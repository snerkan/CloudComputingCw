package com.pg4.cloudcw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pg4.cloudcw.entity.File;
import com.pg4.cloudcw.entity.Folder;
import com.pg4.cloudcw.entity.User;
import com.pg4.cloudcw.service.FileService;
import com.pg4.cloudcw.service.FolderService;
import com.pg4.cloudcw.service.UserService;

@Controller
public class ItemController {

	@Autowired
	private FileService fileService;

	@Autowired
	private FolderService folderService;

	@Autowired
	private UserService userService;

	// TODO: User
	User user;

	
	@GetMapping("/items")
	public String home(Model model) {
		model.addAttribute("files", fileService.getAllByUserId(getUser().getId()));
		model.addAttribute("folders", folderService.getAllByUserId());
		return "items";
	}

	@GetMapping("/items/trash")
	public String trash(Model model) {
		model.addAttribute("files", fileService.getDeletedFilesByUserId(getUser().getId()));
		model.addAttribute("folders", folderService.getDeletedFoldersByUserId());
		return "trash";
	}

	@PostMapping("/items/createFolder")
	public String createFolder(@RequestParam("name") String name, @RequestParam("parentId") String parentId,
			Model model) {		
		if (parentId != null && !parentId.equals("")) {
			folderService.createFolder(new Folder(getUser(), name, Integer.parseInt(parentId)));
		} else {
			folderService.createFolder(new Folder(getUser(), name));
		}
		model.addAttribute("files", fileService.getAllByUserId(getUser().getId()));
		model.addAttribute("folders", folderService.getAllByUserId());
		return "redirect:/items";
	}
	
	
	@PostMapping("/items/createFile")
	public String createFile(@RequestParam("name") String name, @RequestParam("parentId") String parentId,
			Model model) {		
		//TODO: File Upload Address
		if (parentId != null && !parentId.equals("")) {
			fileService.createFile(new File (name, getUser(), "0", null));
		} else {
			fileService.createFile(new File (name, getUser(), "0"));
		}
		model.addAttribute("files", fileService.getAllByUserId(getUser().getId()));
		model.addAttribute("folders", folderService.getAllByUserId());
		return "redirect:/items";
	}

	@GetMapping("items/deleteFile/{id}")
	public String deleteFile(@PathVariable("id") int id, Model model) {
		fileService.deleteFirstTime(id,getUser().getId());
		model.addAttribute("files", fileService.getAllByUserId(getUser().getId()));
		model.addAttribute("folders", folderService.getAllByUserId());
		return "redirect:/items";
	}

	@GetMapping("items/putBack/{id}")
	public String putBackFile(@PathVariable("id") int id, Model model) {
		fileService.putBackFileFromTrash(id, getUser().getId());
		model = prepareModels(model, fileService.getDeletedFilesByUserId(getUser().getId()), folderService.getDeletedFoldersByUserId());
		return "redirect:/items/trash";
	}
	
	@GetMapping("/items/trash/deleteFile/{id}")
	public String deleteFromTrash(@PathVariable("id") int id, Model model) {
		fileService.deleteFirstTime(getUser().getId(),getUser().getId());
		model = prepareModels(model, fileService.getDeletedFilesByUserId(getUser().getId()), folderService.getDeletedFoldersByUserId());
		return "redirect:/items/trash";
	}

	@GetMapping("/items/trash/deleteAllFiles/")
	public String deleteAll(Model model) {
		fileService.deleteAllPermanently(getUser().getId());
		model = prepareModels(model, fileService.getDeletedFilesByUserId(getUser().getId()), folderService.getDeletedFoldersByUserId());
		return "redirect:/items/trash";
	}

	public Model prepareModels(Model model, @Nullable Object fileObj, @Nullable Object folderObj) {
		model.addAttribute("files", fileObj);
		model.addAttribute("folders", folderObj);
		return model;
	}

	// TODO: User
	public User getUser() {
		if (user == null) {
			user = userService.getUserById(1);
			if (user.  equals (null)) {
				user = new User("0", "user");
			}
		}
		return user;
	}
}
