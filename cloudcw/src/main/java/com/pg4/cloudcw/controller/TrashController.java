package com.pg4.cloudcw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pg4.cloudcw.entity.User;
import com.pg4.cloudcw.service.FileService;
import com.pg4.cloudcw.service.FolderService;
import com.pg4.cloudcw.service.UserService;

@Controller
public class TrashController {

	@Autowired
	private FileService fileService;

	@Autowired
	private FolderService folderService;

	@Autowired
	private UserService userService;

	// TODO: User
	User user;
	
	
	@GetMapping("/trash")
	public String trash(Model model) {
		prepareModelsForTrash(model);
		return "/trash";
	}
	

	// put back FILE from Trash
	@GetMapping("/trash/putBackFile/{id}")
	public String putBackFile(@PathVariable("id") int id, Model model) {
		fileService.putBackFileFromTrash(id, getUser().getId());
		model = prepareModelsForTrash(model);
		return "redirect:/trash";
	}

	// put back FOLDER from Trash 
	@GetMapping("/trash/putBackFolder/{id}")
	public String putBackFolder(@PathVariable("id") int id, Model model) {
		folderService.putBackFolderFromTrash(id, getUser().getId());
		model = prepareModelsForTrash(model);
		return "redirect:/trash";
	}	
	
	// Delete FILE from Trash
	@GetMapping("/trash/deleteFile/{id}")
	public String deleteFileFromTrash(@PathVariable("id") int id, Model model) {
		fileService.deletePermanently(id);
		model = prepareModelsForTrash(model);
		return "redirect:/trash";
	}
	
	// Delete FOLDER from Trash
	@GetMapping("/trash/deleteFolder/{id}")
	public String deleteFolderFromTrash(@PathVariable("id") int id, Model model) {
		folderService.deletePermanently(id);
		model = prepareModelsForTrash(model); 
		return "redirect:/trash";
	}

	
	// Delete ALL from Trash
	@GetMapping("/trash/deleteAllItems/")
	public String deleteAll(Model model) {
		fileService.deleteAllPermanently(getUser().getId());
		folderService.deleteAllPermanently(getUser().getId());
		model = prepareModelsForTrash(model);
		return "redirect:/trash";
	}
	
	public Model prepareModelsForTrash(Model model) {
		model.addAttribute("files", fileService.getDeletedFilesByUserId(getUser().getId()));
		model.addAttribute("folders", folderService.getDeletedFoldersByUserId(getUser().getId()));
		return model;
	}
	
	// TODO: User
	public User getUser() {
		if (user == null) {
			user = userService.getUserById(1);
			if (user.equals(null)) {
				user = new User("user");
			}
		}
		return user;
	}
}
