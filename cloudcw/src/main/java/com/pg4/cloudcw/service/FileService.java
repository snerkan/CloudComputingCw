package com.pg4.cloudcw.service;

import java.util.ArrayList;
import java.util.List;

import com.pg4.cloudcw.dao.FileRepository;
import com.pg4.cloudcw.entity.File;

public class FileService {

	private final FileRepository fileRepository;
	
	 public FileService(FileRepository fileRepository) {
		 this.fileRepository=fileRepository;
	 }
	 
	 public List <File> getAll(){
		 List <File> files = new ArrayList<>();
		 
		 for(File f : fileRepository.findAll()) {
			 files.add(f);
		 }
		 return files;
	 }
	 
	 public void create(File newFile) {
		 fileRepository.save(newFile);
	 }
	 
	 public void delete(File file) {
		 fileRepository.delete(file);
	 }
	 
	 public void deleteFirstTime(File file) {
		 file.setDeleted(true);
		 fileRepository.save(file);
	 }
}
