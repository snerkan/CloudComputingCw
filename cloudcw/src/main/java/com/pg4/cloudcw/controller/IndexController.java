package com.pg4.cloudcw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.pg4.cloudcw.service.FileService;

@Controller
public class IndexController {

	@Autowired
	private FileService fileService;
	
	@GetMapping("/")
    public String home(HttpServletRequest request) {
		//request.setAttribute("files", fileService.getAllByUserId());
        return "index";
    }
	
	/*@PostMapping("/index/createFolder")
	public String createFolder(HttpServletRequest request) {
		return "redirect:index";
    }*/
}




	/*	//@PostMapping("index")
	@RequestMapping(value = "/index",method = RequestMethod.POST)
    public String createFolder(@ModelAttribute Folder folder) {
        return "index";
    }
	
	

	@PostMapping("/index/createFolder")
    public String createFolder2(@ModelAttribute Folder folder) {
        return "index";
    }
	
	@GetMapping("/newFile")
    public String createFile(HttpServletRequest request) {
		request.setAttribute("files", "newFile");
        return "index";
    }


	

	
*/
	/*
	@GetMapping("/updateFile")
	public String updateFileName(@RequestParam int id,@RequestParam String newName, HttpServletRequest request) {
		request.setAttribute("file", fileService.updateFileName(id, newName) );
		 return "index";
	}
	
	@GetMapping("/deleteFile")
	public String deleteFile(@RequestParam int id, HttpServletRequest request) {
		fileService.deleteFirstTime(id);
	//	request.setAttribute("file", fileService.updateFileName(id, newName) );
		 return "index";
	}
	
	@GetMapping("/deleteFile")
	public String deleteFromTrash(@RequestParam int id, HttpServletRequest request) {
		fileService.delete(id);
		//request.setAttribute("files", fileService.getAll());
		 return "index";
	}
	
	    @PutMapping("/{id}")
	    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
	        if (book.getId() != id) {
	          throw new BookIdMismatchException();
	        }
	        bookRepository.findOne(id)
	          .orElseThrow(BookNotFoundException::new);
	        return bookRepository.save(book);
	    }
	
	
	@PostMapping("article")
	public ResponseEntity<Void> addArticle(@RequestBody Article article, UriComponentsBuilder builder) {
                boolean flag = articleService.addArticle(article);
                if (flag == false) {
        	    return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(builder.path("/article/{id}").buildAndExpand(article.getArticleId()).toUri());
                return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	@PutMapping("article")
	public ResponseEntity<Article> updateArticle(@RequestBody Article article) {
		articleService.updateArticle(article);
		return new ResponseEntity<Article>(article, HttpStatus.OK);
	}
		public void createFile() {
		//File newFile = new File (,"0");
	//	fileService.create(newFile);	
	}
	
	
	*/
	


