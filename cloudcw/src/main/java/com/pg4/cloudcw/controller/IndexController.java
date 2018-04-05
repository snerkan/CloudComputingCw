package com.pg4.cloudcw.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.pg4.cloudcw.service.FileService;

@Controller
public class IndexController {
	private static final Collection<String> SCOPES = Arrays.asList("email", "profile");
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	
	@Autowired
	private com.pg4.cloudcw.service.UserService userService;

	@GetMapping("/")
	public String home(HttpServletRequest request) {
		// request.setAttribute("files", fileService.getAllByUserId());
		return "index";
	}

	@PostMapping("/index/usersignin")
	public String userLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
				.setAudience(Collections
						.singletonList("629296123157-k3ronshj46oauuvlss5hd1agig4vilrq.apps.googleusercontent.com"))
				.build();

		// (Receive idTokenString by HTTPS POST)
		String idTokenString=req.getParameter("idtoken");
		GoogleIdToken idToken=null;
		try {
			idToken = verifier.verify(idTokenString);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (idToken != null) {
			Payload payload = idToken.getPayload();

			// Print user identifier
			/*String userId = payload.getSubject();
			System.out.println("User ID: " + userId);*/

			// Get profile information from payload
			String email = payload.getEmail();
			com.pg4.cloudcw.entity.User user = userService.getUserByEmail(email);
			if(user==null) {
				user = new com.pg4.cloudcw.entity.User(email);
				userService.createNewUser(user);
			}
			return "items";
		} else {
			System.out.println("Invalid ID token.");
			return "index";
		}
	}
}

