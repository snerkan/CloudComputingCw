package com.pg4.cloudcw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.pg4.cloudcw.dao.FolderRepository;
import com.pg4.cloudcw.entity.Folder;

@Service
@Transactional
public class FolderService {
	
	private final FolderRepository folderRepository;

	 public FolderService(FolderRepository folderRepository) {
		 this.folderRepository=folderRepository;
	 }
	 
	 public List <Folder> getAllByUserId(){
		 List <Folder> folders = new ArrayList<>();
		 int userID=1;
		 for(Folder f : folderRepository.findByUserIdAndIsDeleted(userID,false)) {
			 folders.add(f);
		 }
		 return folders;
	 }
	 
	 //For Trash folder
	 public List <Folder> getDeletedFoldersByUserId(){
		 List <Folder> folders = new ArrayList<>();
		 int userID=1;
		 for(Folder f : folderRepository.findByUserIdAndIsDeleted(userID,true)) {
			 folders.add(f);
		 }
		 return folders;
	 }
	 
	public void createFolder(Folder newFolder) {
		folderRepository.save(newFolder);
	 }
	 
	 //For Trash folder
	 public void deleteFirstTime(Folder folder) {
		 folder.setDeleted(true);
		 folderRepository.save(folder);
	 }
	
	 public void deletePermanently(Folder folder) {
		 folderRepository.delete(folder);
	 }
}
