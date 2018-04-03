package com.pg4.cloudcw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pg4.cloudcw.dao.FolderRepository;
import com.pg4.cloudcw.entity.File;
import com.pg4.cloudcw.entity.Folder;

@Service
@Transactional
public class FolderService {

	private final FolderRepository folderRepository;

	public FolderService(FolderRepository folderRepository) {
		this.folderRepository = folderRepository;
	}

	// Get all Folders By User Id
	public List<Folder> getAllByUserId(int userId) {
		List<Folder> folders = new ArrayList<>();
		for (Folder f : folderRepository.findByUserIdAndIsDeleted(userId, false)) {
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
		folderRepository.save(newFolder);
	}
	
	// For Trash folder
	public void renameFolder(int id, int userId, String NewName) {
		Folder folder = folderRepository.findById(id);
		if (userId == folder.getUser().getId()) {
			folder.setName(NewName);
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
	public void deletePermanently(Folder folder) {
		folderRepository.delete(folder);
	}
}
