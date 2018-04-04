package com.pg4.cloudcw.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.apache.commons.fileupload.FileItemStream;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.sql.jdbc.internal.Url;
import com.pg4.cloudcw.cloudStorage.CloudStorageHelper;
import com.pg4.cloudcw.dao.FileRepository;
import com.pg4.cloudcw.entity.File;
import com.pg4.cloudcw.entity.Flag;
import com.pg4.cloudcw.entity.Folder;
import com.pg4.cloudcw.entity.User;

@Service
@Transactional
public class FileService {

	private static final Logger log = LoggerFactory.getLogger(FileService.class);

	private final FileRepository fileRepository;

	CloudStorageHelper csh;

	public FileService(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
		csh = new CloudStorageHelper();
	}

	// Get all Files By User Id
	public List<File> getAllByUserId(int userId) {
		List<File> files = new ArrayList<>();
		for (File f : fileRepository.findByUserIdAndIsDeleted(userId, false)) {
			files.add(f);
		}
		return files;
	}
	
	// Get all Files  By User and Folder
	public List<File> getAllByUserIdandFolder(int userId,int folderId) {
		List<File> files = new ArrayList<>();
		for (File f : fileRepository.findByUserIdAndFolderIdAndIsDeleted(userId, folderId, false)) {
			files.add(f);
		}
		return files;
	}

	// For Trash folder
	public List<File> getDeletedFilesByUserId(int userId) {
		List<File> files = new ArrayList<>();
		for (File f : fileRepository.findByUserIdAndIsDeleted(userId, true)) {
			files.add(f);
		}
		return files;
	}

	// @Async
	public void uploadFile(FileItemStream fileStream, Folder folder, User user) throws InterruptedException {

		// log.info("###Start Processing with Thread id: " +
		// Thread.currentThread().getId());

		// For unique name
		String uniqueName = DateTime.now(DateTimeZone.UTC).toString(DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS"));
		String fileFirstName = fileStream.getName();
		int index = fileFirstName.lastIndexOf('.');
		String type = fileFirstName.substring(index);
		uniqueName = fileFirstName.substring(0, index) + uniqueName + type;

		try {
			String address = csh.uploadFile(fileStream, uniqueName);
			if (fileRepository.existsByName(fileFirstName)) {
				fileFirstName = uniqueName;
			}
			fileRepository.save(new File(fileFirstName, user, folder, address, uniqueName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	// Rename folder
	public void renameFile(int id, int userId, String newName) {
		File file = fileRepository.findById(id);
		if (userId == file.getUser().getId()) {
			file.setName(newName);
			fileRepository.save(file);
		}
	}

	// For changing Current Directory
	public void changeFileFlag(int id, int userId, Flag newFlag) {
		File file = fileRepository.findById(id);
		if (userId == file.getUser().getId()) {
			file.setFlag(newFlag);
			fileRepository.save(file);
		}
	}
	
	// For changing Current Directory
	public void changeFileDirectory(int id, int userId, Folder folder) {
		File file = fileRepository.findById(id);
		if (userId == file.getUser().getId()) {
			file.setFolder(folder);
			fileRepository.save(file);
		}
	}

	// For Trash folder
	public void deleteFirstTime(int id, int userId) {
		File file = fileRepository.findById(id);
		if (userId == file.getUser().getId()) {
			file.setDeleted(true);
			fileRepository.save(file);
		}
	}

	// Put Back File from trash
	public void putBackFileFromTrash(int id, int userId) {
		File file = fileRepository.findById(id);
		if (userId == file.getUser().getId()) {
			file.setDeleted(false);
			fileRepository.save(file);
		}
	}

	// Delete Permanently from Trash
	public void deletePermanently(int id) {
		File file = fileRepository.findById(id);
		try {
			csh.deleteItem(file.getStorageName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		fileRepository.deleteById(id);
	}

	// Delete All Folders Permanently from Trash
	public void deleteAllPermanently(int userId) {
		List <File> fileList = fileRepository.findByUserIdAndIsDeleted(userId, true);
		for (File file : fileList) { // TODO: User Check
			if (file.getUser().getId() == userId) {
				try {
					csh.deleteItem(file.getStorageName());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fileRepository.delete(file);
			}
		}
	}
}
