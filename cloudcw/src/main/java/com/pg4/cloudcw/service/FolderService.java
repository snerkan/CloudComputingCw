package com.pg4.cloudcw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import com.pg4.cloudcw.dao.FolderRepository;
import com.pg4.cloudcw.entity.File;
import com.pg4.cloudcw.entity.Flag;
import com.pg4.cloudcw.entity.Folder;

@Service
@Transactional
public class FolderService {

	private final FolderRepository folderRepository;

	public FolderService(FolderRepository folderRepository) {
		this.folderRepository = folderRepository;
	}

	// Get a folder By Id
	public Folder getFolderById(int id) {
		return folderRepository.findById(id);
	}
	
	// Get all Folders By User Id
	public List<Folder> getAllByUserId(int userId) {
		List<Folder> folders = new ArrayList<>();
		for (Folder f : folderRepository.findByUserIdAndIsDeleted(userId, false)) {
			folders.add(f);
		}
		return folders;
	}
	
	// Get all Folders By User and Folder
	public List<Folder> getAllByUserIdandFolder(int userId, int folderId) {
		List<Folder> folders = new ArrayList<>();
		for (Folder f : folderRepository.findByUserIdAndParentFolderIdAndIsDeleted(userId, folderId,false)) {
			folders.add(f);
		}
		return folders;
	}

	// For Trash folder
	public List<Folder> getDeletedFoldersByUserId(int userId) {
		List<Folder> folders = new ArrayList<>();
		for (Folder f : folderRepository.findByUserIdAndIsDeleted(userId, true)) {
			folders.add(f);
		}
		return folders;
	}

	public void createFolder(Folder newFolder) {
		if(folderRepository.existsByName(newFolder.getName())){
			String uniqueName = DateTime.now(DateTimeZone.UTC).toString(DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmss"));
			newFolder.setName(newFolder.getName()+ uniqueName);
		}
		folderRepository.save(newFolder);
	}
	
	// Rename folder
	public void renameFolder(int id, int userId, String newName) {
		Folder folder = folderRepository.findById(id);
		if (userId == folder.getUser().getId()) {
			folder.setName(newName);
			folderRepository.save(folder);
		}
	}
	
	// Change folder flag
	public void changeFolderFlag(int id, int userId, Flag newFlag) {
		Folder folder = folderRepository.findById(id);
		if (userId == folder.getUser().getId()) {
			folder.setFlag(newFlag);
			folderRepository.save(folder);
		}
	}
	
	// For changing Current Directory
	public void changeFolderDirectory(int id, int userId, int parentFolderId) {
		Folder folder = folderRepository.findById(id);
		if (userId == folder.getUser().getId()) {
			folder.setParentFolderId(parentFolderId);
			folderRepository.save(folder);
		}
	}
	
	// For Trash folder
	public void deleteFirstTime(int id, int userId) {
		Folder folder = folderRepository.findById(id);
		if (userId == folder.getUser().getId()) {
			folder.setDeleted(true);
			folderRepository.save(folder);
		}
	}
	
	// Put Back Folder from trash
	public void putBackFolderFromTrash(int id, int userId) {
		Folder folder = folderRepository.findById(id);
		if (userId == folder.getUser().getId()) {
			folder.setDeleted(false);
			folderRepository.save(folder);
		}
	}

	//Delete Permanently from Trash
	public void deletePermanently(int folderId) {
		folderRepository.deleteById(folderId);
	}
	
	// Delete All Files Permanently from Trash
	public void deleteAllPermanently(int userId) {
		List<Folder> folderList = folderRepository.findByUserIdAndIsDeleted(userId, true);
		for (Folder folder : folderList) { // TODO: User Check
			if (folder.getUser().getId() == userId) {
				folderRepository.delete(folder);
			}
		}
	}
}
