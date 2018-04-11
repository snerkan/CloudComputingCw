package com.pg4.cloudcw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private FileService fileService;

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
	public List<Folder> getAllByUserIdAndFolder(int userId, int folderId) {
		List<Folder> folders = new ArrayList<>();
		for (Folder f : folderRepository.findByUserIdAndParentFolderIdAndIsDeleted(userId, folderId, false)) {
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
		if (folderRepository.existsByName(newFolder.getName())) {
			String uniqueName = DateTime.now(DateTimeZone.UTC)
					.toString(DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmss"));
			newFolder.setName(newFolder.getName() + uniqueName);
		}
		folderRepository.save(newFolder);
	}

	// Rename folder
	public void renameFolder(int id, int userId, String newName) {
		Folder folder = folderRepository.findById(id);
		if (userId == folder.getUser().getId()) {
			if (folderRepository.existsByName(newName)) {
				newName = newName + DateTime.now(DateTimeZone.UTC).toString(DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmss"));
			}
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
	public void deleteFirstTime(Folder folder) {
		for (File childFile : fileService.getAllByUserIdAndFolder(folder.getUser().getId(),folder.getId(),false)) {
			System.out.println("delete files" +fileService);
			fileService.deleteFirstTime(childFile);
		}
		System.out.println("delete files2");
		List<Folder> childFolders = folderRepository.findByUserIdAndParentFolderId(folder.getUser().getId(),folder.getId());
		for (Folder childFolder : childFolders) {
			System.out.println("delete folders");
			deleteFirstTime(childFolder);
		}
		System.out.println("set deleted");
		folder.setDeleted(true);
		folderRepository.save(folder);
	}

	// Put Back Folder from trash
	public void putBackFolderFromTrash(Folder folder) {
		for (File childFile : fileService.getAllByUserIdAndFolder(folder.getUser().getId(),folder.getId(),true)) {
			fileService.putBackFileFromTrash(childFile);
		}
		List<Folder> childFolders = folderRepository.findByUserIdAndParentFolderId(folder.getUser().getId(),
				folder.getId());
		for (Folder childFolder : childFolders) {
			putBackFolderFromTrash(childFolder);
		}
		folder.setDeleted(false);
		folderRepository.save(folder);
	}

	// Delete Permanently from Trash
	public void deletePermanently(Folder folder) {
		for (File childFile : fileService.getAllByUserIdAndFolder(folder.getUser().getId(),folder.getId(),true)) {
			fileService.deletePermanently(childFile);
		}
		List<Folder> childFolders = folderRepository.findByUserIdAndParentFolderId(folder.getUser().getId(),
				folder.getId());
		for (Folder childFolder : childFolders) {
			deletePermanently(childFolder);
		}
		folderRepository.delete(folder);
	}

	// Delete All Folder Permanently from Trash
	public void deleteAllPermanently(int userId) {
		List<Folder> folderList = folderRepository.findByUserIdAndIsDeleted(userId, true);
		for (Folder folder : folderList) { // TODO: User Check
			if (folder.getUser().getId() == userId) {
				folderRepository.delete(folder);
			}
		}
	}
}
