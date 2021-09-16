package com.thecodeveal.app.controllers;

import com.thecodeveal.app.entities.User;
import com.thecodeveal.app.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class AppController {
	
	@Autowired
	UserDetailsRepository userRepo;

	@GetMapping
	public String testApp() {
		return "Hello Spring Security!";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(path = "/showUsers")
	public ResponseEntity<List<User>> showUsers() {
		List<User> usuarios = userRepo.findAll();
		return new ResponseEntity<>(usuarios, HttpStatus.OK);
	}

}
