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
		prepareModelsForIndex(model);
		return "items";
	}

	@GetMapping("/items/trash")
	public String trash(Model model) {
		prepareModelsForTrash(model);
		return "/trash";
	}
	
	// Create Folder
	@PostMapping("/items/createFolder")
	public String createFolder(@RequestParam("name") String name, @RequestParam("parentId") String parentId,
			Model model) {
		int parenId = Integer.parseInt(parentId);
		folderService.createFolder( new Folder(name,getUser(), parenId));			
		prepareModelsForIndex(model);
		return "redirect:/items";
	}

	// Upload File
	@PostMapping("/items/createFile")
	public void createFile(HttpServletRequest request, Model model, HttpServletResponse response) throws ServletException, IOException {
		try {
			Folder parentFolder = null;
			int folderId = 0;
			if(folderId!=0) {
				parentFolder = folderService.getFolderById(0);
			}
			FileItemIterator iterator = new ServletFileUpload().getItemIterator(request);
			while (iterator.hasNext()) {
				FileItemStream fileStream = iterator.next();
				try {
					fileService.uploadFile(fileStream, parentFolder, getUser());
				} catch (InterruptedException e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileUploadException e1) { // TODO Auto-generated catch block
			e1.printStackTrace();
		}
		prepareModelsForIndex(model);
		response.sendRedirect("/items");
	}
	
	
	
	
	/*
	 
		// Upload File
	@PostMapping("/items/createFile")
	public void createFile(HttpServletRequest request, Model model, HttpServletResponse response)
			throws ServletException, IOException {
		Folder parentFolder = null;
		FileItemStream fileParent = null;
		FileItemIterator iterator=null;
		try {
			iterator = new ServletFileUpload().getItemIterator(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while (iterator.hasNext()) {
				FileItemStream fileStream = iterator.next();
				if (fileStream.getFieldName().equals("file")) {
					if (iterator.hasNext()) {
						fileParent = iterator.next();
						if (fileParent.getFieldName().equals("parentId")) {
							parentFolder = folderService.getFolderById(0);//Integer.parseInt(fileParent.getName()));
						}
					}
					try {
						fileService.uploadFile(fileStream, null, getUser());
					} catch (InterruptedException e) { // TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prepareModelsForIndex(model);
		response.sendRedirect("/items.jsp");
	}
	  */
	

	//Change FILE Flag
	@GetMapping("/items/changeFileFlag/{id}")
	public String changeFileFlag(@PathVariable("id") int id, @RequestParam("newflag") Flag newflag, Model model) {
		fileService.changeFileFlag(id, getUser().getId(), newflag);
		prepareModelsForIndex(model);
		return "redirect:/items";
	}

	
	//Change FOLDER Flag
	@GetMapping("/items/changeFolderFlag/{id}")
	public String changeFolderFlag(@PathVariable("id") int id, @RequestParam("newflag") Flag newflag, Model model) {
		folderService.changeFolderFlag(id, getUser().getId(), newflag);
		prepareModelsForIndex(model);
		return "redirect:/items";
	}
	
	//Change FILE directory
	@GetMapping("/items/changeFileDirectory/{id}")
	public String changeFileDirectory(@PathVariable("id") int id, @RequestParam("folderId") Folder folder, Model model) {
		fileService.changeFileDirectory(id, getUser().getId(), folder);
		prepareModelsForIndex(model);
		return "redirect:/items";
	}

	
	//Change FOLDER Directory
	@GetMapping("/items/changeFolderDirectory/{id}")
	public String changeFolderDirectory(@PathVariable("id") int id,@RequestParam("folderId") Folder folder, Model model) {
		folderService.changeFolderDirectory(id, getUser().getId(), folder.getId());
		prepareModelsForIndex(model);
		return "redirect:/items";
	}
	
	//Rename FILE
	@GetMapping("/items/renameFile/{id}")
	public String renameFile(@PathVariable("id") int id, @RequestParam("newName") String newName, Model model) {
		fileService.renameFile(id, getUser().getId(), newName);
		prepareModelsForIndex(model);
		return "redirect:/items";
	}

	
	//Rename FOLDER
	@GetMapping("/items/renameFolder/{id}")
	public String renameFolder(@PathVariable("id") int id, @RequestParam("newName") String newName, Model model) {
		folderService.renameFolder(id, getUser().getId(), newName);
		prepareModelsForIndex(model);
		return "redirect:/items";
	}

	
	//Delete FILE
	@GetMapping("/items/deleteFile/{id}")
	public String deleteFile(@PathVariable("id") int id, Model model) {
		fileService.deleteFirstTime(id, getUser().getId());
		prepareModelsForIndex(model);
		return "redirect:/items";
	}

	
	//Delete FOLDER
	@GetMapping("/items/deleteFolder/{id}")
	public String deleteFolder(@PathVariable("id") int id, Model model) {
		folderService.deleteFirstTime(id, getUser().getId());
		prepareModelsForIndex(model);
		return "redirect:/items";
	}
	
	
	// put back FILE from Trash
	@GetMapping("/items/trash/putBackFile/{id}")
	public String putBackFile(@PathVariable("id") int id, Model model) {
		fileService.putBackFileFromTrash(id, getUser().getId());
		model = prepareModelsForTrash(model);
		return "redirect:/items/trash";
	}

	// put back FOLDER from Trash 
	@GetMapping("/items/trash/putBackFolder/{id}")
	public String putBackFolder(@PathVariable("id") int id, Model model) {
		folderService.putBackFolderFromTrash(id, getUser().getId());
		model = prepareModelsForTrash(model);
		return "redirect:/items/trash";
	}	
	
	// Delete FILE from Trash
	@GetMapping("/items/trash/deleteFile/{id}")
	public String deleteFileFromTrash(@PathVariable("id") int id, Model model) {
		fileService.deletePermanently(id);
		model = prepareModelsForTrash(model);
		return "redirect:/items/trash";
	}
	
	// Delete FOLDER from Trash
	@GetMapping("/items/trash/deleteFolder/{id}")
	public String deleteFolderFromTrash(@PathVariable("id") int id, Model model) {
		folderService.deletePermanently(id);
		model = prepareModelsForTrash(model); 
		return "redirect:/items/trash";
	}

	
	
	// Delete ALL from Trash
	@GetMapping("/items/trash/deleteAllItems/")
	public String deleteAll(Model model) {
		fileService.deleteAllPermanently(getUser().getId());
		folderService.deleteAllPermanently(getUser().getId());
		model = prepareModels(model, fileService.getDeletedFilesByUserId(getUser().getId()),
				folderService.getDeletedFoldersByUserId(getUser().getId()));
		return "redirect:/items/trash";
	}
	
	
	public void prepareModelsForIndex(Model model) {
		model.addAttribute("files", fileService.getAllByUserId(getUser().getId()));
		model.addAttribute("folders", folderService.getAllByUserId(getUser().getId()));
	//	return model;
	}
	
	public Model prepareModelsForTrash(Model model) {
		model.addAttribute("files", fileService.getDeletedFilesByUserId(getUser().getId()));
		model.addAttribute("folders", folderService.getDeletedFoldersByUserId(getUser().getId()));
		return model;
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
