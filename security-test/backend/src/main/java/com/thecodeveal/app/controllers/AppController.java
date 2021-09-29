package com.thecodeveal.app.controllers;

import com.thecodeveal.app.entities.User;
import com.thecodeveal.app.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/users/")
public class AppController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private JavaMailSender emailSender;

	private String mailPassChange;

	@Value("${mail.username}")
	private String mailRecovery;
	
	@Autowired
	UserDetailsRepository userRepo;

	@Autowired
	public AppController(JavaMailSender emailSender, UserDetailsRepository repo) {
		this.emailSender = emailSender;
		this.userRepo = repo;
	}

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

	@PostMapping(path = "/passRecover")
	public ResponseEntity<String> passRecover(String correo) {
		String token = UUID.randomUUID().toString();

		User usuario = userRepo.findByEmail(correo);
		this.mailPassChange = correo;

		String url = "http://localhost:3000/";
		String bodyMessage = "Para cambiar la contraseña, click aqui: " + url;

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(correo);
		message.setFrom(mailRecovery);
		message.setSubject("Recuperación de contraeña para el usuario: " + usuario.getUserName());
		message.setText(bodyMessage);

		emailSender.send(message);
		return new ResponseEntity<>(token, HttpStatus.OK);
	}

	@PatchMapping(path = "/passChange")
	public ResponseEntity<?> passChange(@RequestBody String newPass) {
		User usuario = userRepo.findByEmail(this.mailPassChange);
		usuario.setPassword(passwordEncoder.encode(newPass));
		userRepo.save(usuario);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
