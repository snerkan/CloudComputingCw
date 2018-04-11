package com.pg4.cloudcw.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pg4.cloudcw.entity.File;
import com.pg4.cloudcw.entity.Flag;
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
		prepareModelsForIndex(model, 0);
		return "items";
	}

	@GetMapping("/items/folder/{id}")
	public String homeForFolder(@PathVariable("id") int id, Model model) {
		prepareModelsForIndex(model, id);
		return "items";
	}

	// Create Folder
	@PostMapping("/items/createFolder")
	public String createFolder(@RequestParam("name") String name, @RequestParam("parentId") String parentId,
			Model model) {
		int parenId = Integer.parseInt(parentId);
		folderService.createFolder(new Folder(name, getUser(), parenId));
		prepareModelsForIndex(model);
		return "redirect:/items";
	}

	// Upload File
	@PostMapping("/items/createFile/{folderId}")
	public void createFile(@PathVariable("folderId") int folderId, HttpServletRequest request, Model model, HttpServletResponse response) throws ServletException, IOException {
		try {
			Folder parentFolder = folderService.getFolderById(folderId);
			// Getting files
			FileItemIterator iterator = new ServletFileUpload().getItemIterator(request);
			FileItemStream fileStream;
			while (iterator.hasNext()) {
				fileStream = iterator.next();
				if (fileStream.getFieldName().equals("file")) {
					try {
						fileService.uploadFile(fileStream, parentFolder, getUser());
					} catch (InterruptedException e) { // TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (FileUploadException e1) { // TODO Auto-generated catch block
			e1.printStackTrace();
		}
		prepareModelsForIndex(model, folderId);
		response.sendRedirect("/items");
		
	}

	// Change FILE Flag
	@GetMapping("/items/changeFileFlag/{id}")
	public String changeFileFlag(@PathVariable("id") int id, @RequestParam("newflag") Flag newflag, Model model) {
		fileService.changeFileFlag(id, getUser().getId(), newflag);
		prepareModelsForIndex(model);
		return "redirect:/items";
	}

	// Change FOLDER Flag
	@GetMapping("/items/changeFolderFlag/{id}")
	public String changeFolderFlag(@PathVariable("id") int id, @RequestParam("newflag") Flag newflag, Model model) {
		folderService.changeFolderFlag(id, getUser().getId(), newflag);
		prepareModelsForIndex(model);
		return "redirect:/items";
	}

	// Change FILE directory
	@GetMapping("/items/changeFileDirectory/{id}")
	public String changeFileDirectory(@PathVariable("id") int id, @RequestParam("folderId") Folder folder,
			Model model) {
		fileService.changeFileDirectory(id, getUser().getId(), folder);
		prepareModelsForIndex(model);
		return "redirect:/items";
	}

	// Change FOLDER Directory
	@GetMapping("/items/changeFolderDirectory/{id}")
	public String changeFolderDirectory(@PathVariable("id") int id, @RequestParam("folderId") Folder folder,
			Model model) {
		folderService.changeFolderDirectory(id, getUser().getId(), folder.getId());
		prepareModelsForIndex(model);
		return "redirect:/items";
	}

	// Rename FILE
	@PostMapping("/items/renameFile/{id}")
	public String renameFile(@PathVariable("id") int id, @RequestParam("newName") String newName, Model model) {
		try {
			fileService.renameFile(id, getUser().getId(), newName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prepareModelsForIndex(model);
		return "redirect:/items";
	}

	// Rename FOLDER
	@PostMapping("/items/renameFolder/{id}")
	public String renameFolder(@PathVariable("id") int id, @RequestParam("newName") String newName, Model model) {
		folderService.renameFolder(id, getUser().getId(), newName);
		prepareModelsForIndex(model);
		return "redirect:/items";
	}

	// Delete FILE
	@GetMapping("/items/deleteFile/{id}")
	public String deleteFile(@PathVariable("id") int id, Model model) {
		File file = fileService.getFileById(id);
		if (file.getUser().getId() == getUser().getId()) {
			fileService.deleteFirstTime(file);
		}
		prepareModelsForIndex(model);
		return "redirect:/items";
	}

	// Delete FOLDER
	@GetMapping("/items/deleteFolder/{id}")
	public String deleteFolder(@PathVariable("id") int id, Model model) {
		Folder folder = folderService.getFolderById(id);
		if (folder.getUser().getId() == getUser().getId()) {
			folderService.deleteFirstTime(folder);
		}
		prepareModelsForIndex(model);
		return "redirect:/items";
	}

	public void prepareModelsForIndex(Model model) {
		model.addAttribute("files", fileService.getAllByUserId(getUser().getId()));
		model.addAttribute("folders", folderService.getAllByUserId(getUser().getId()));
		model.addAttribute("folderId", 0);
		model.addAttribute("flags", Flag.values());
	}

	public void prepareModelsForIndex(Model model, int folderId) {
		model.addAttribute("files", fileService.getAllByUserIdAndFolder(getUser().getId(), folderId,false));
		model.addAttribute("folders", folderService.getAllByUserIdAndFolder(getUser().getId(), folderId));
		model.addAttribute("folderId", folderId);
		model.addAttribute("flags", Flag.values());
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
			if (user.equals(null)) {
				user = new User("user");
			}
		}
		return user;
	}
}
