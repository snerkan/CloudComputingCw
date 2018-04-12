package com.pg4.cloudcw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pg4.cloudcw.entity.File;
import com.pg4.cloudcw.entity.Folder;
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
		File file = fileService.getFileById(id);
		if(file.getUser().getId()==getUser().getId()) {
			fileService.putBackFileFromTrash(file);
		}
		model = prepareModelsForTrash(model);
		return "redirect:/trash";
	}

	// put back FOLDER from Trash 
	@GetMapping("/trash/putBackFolder/{id}")
	public String putBackFolder(@PathVariable("id") int id, Model model) {
		Folder folder = folderService.getFolderById(id);
		if(folder.getUser().getId()==getUser().getId()) {
			folderService.putBackFolderFromTrash(folder);
		}	
		model = prepareModelsForTrash(model);
		return "redirect:/trash";
	}	
	
	// Delete FILE from Trash
	@GetMapping("/trash/deleteFile/{id}")
	public String deleteFileFromTrash(@PathVariable("id") int id, Model model) {
		File file = fileService.getFileById(id);
		if(file.getUser().getId()==getUser().getId()) {
			fileService.deletePermanently(file);
		}
		model = prepareModelsForTrash(model);
		return "redirect:/trash";
	}
	
	// Delete FOLDER from Trash
	@GetMapping("/trash/deleteFolder/{id}")
	public String deleteFolderFromTrash(@PathVariable("id") int id, Model model) {
		Folder folder = folderService.getFolderById(id);
		if(folder.getUser().getId()==getUser().getId()) {
			folderService.deletePermanently(folder);
		}	
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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = "";
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
			email = (String) oAuth2User.getAttributes().get("email");

			user = userService.getUserByEmail(email);
			if (user == null) {
				user = new User(email);
				userService.createNewUser(user);
				user = userService.getUserByEmail(email);
			}
		}
		return user;
	}
}
