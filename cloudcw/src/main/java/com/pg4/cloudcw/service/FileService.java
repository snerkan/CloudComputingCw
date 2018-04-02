package com.pg4.cloudcw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pg4.cloudcw.dao.FileRepository;
import com.pg4.cloudcw.entity.File;

@Service
@Transactional
public class FileService {

	private final FileRepository fileRepository;

	public FileService(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	public List<File> getAllByUserId(int userId) {
		List<File> files = new ArrayList<>();
		for (File f : fileRepository.findByUserIdAndIsDeleted(userId, false)) {
			files.add(f);
		}
		return files;
	}

	public List<File> getDeletedFilesByUserId(int userId) {
		List<File> files = new ArrayList<>();
		for (File f : fileRepository.findByUserIdAndIsDeleted(userId, true)) {
			files.add(f);
		}
		return files;
	}

	public void createFile(File newFile) {
		fileRepository.save(newFile);
	}

	public void putBackFileFromTrash(int id, int userId) {
		File file = fileRepository.findById(id);
		if(userId == file.getUser().getId()) {
			file.setDeleted(false); 
			fileRepository.save(file);
		}
	}
	
	public void deleteFirstTime(int id, int userId) {
		File file = fileRepository.findById(id);
		if(userId == file.getUser().getId()) {
			file.setDeleted(true); 
			fileRepository.save(file);
		}
	}

	public void deletePermanently(int id) {
		fileRepository.deleteById(id);
	}

	public void deleteAllPermanently(int userId) {
		for (File file : fileRepository.findByUserIdAndIsDeleted(userId, true)) {   //TODO: User Check
			if(file.getUser().getId() == userId) {
				fileRepository.delete(file);
			}
		}
	}
}
