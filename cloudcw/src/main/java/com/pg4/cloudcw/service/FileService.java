package com.pg4.cloudcw.service;

import java.util.ArrayList;
import java.util.List;

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
		 this.fileRepository=fileRepository;
	 }
	
	 public List <File> getAllByUserId(){
		 List <File> files = new ArrayList<>();
		 int userID=1;
		 for(File f : fileRepository.findByUserIdAndIsDeleted(userID,false)) {
			 files.add(f);
		 }
		 return files;
	 }
	 
	 public List <File> getDeletedFilesByUserId(){
		 List <File> files = new ArrayList<>();
		 int userID=1;
		 for(File f : fileRepository.findByUserIdAndIsDeleted(userID,true)) {
			 files.add(f);
		 }
		 return files;
	 }
	  
	 public void createFile(File newFile) {
		 fileRepository.save(newFile);
	 }
	 
	 public void deleteFirstTime(File file) {
		 file.setDeleted(true);
		 fileRepository.save(file);
	 }
	 
	 public void deletePermanently(File file) {
		 fileRepository.delete(file);
	 }

	 
	 /*
	  *   
	 public List <File> getAll(){
		 List <File> files = new ArrayList<>();
		 
		 for(File f : fileRepository.findAll()) {
			 files.add(f);
		 }
		 return files;
	 }
	 
*/
}
