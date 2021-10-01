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
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/users/")
public class AppController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private JavaMailSender emailSender;

	private String mailPassChange;

	@Value("${mail.username}")
	private String mailRecovery;
	
	@Autowired
	private UserDetailsRepository userRepo;

	private String token;

	@Autowired
	public AppController(JavaMailSender emailSender, UserDetailsRepository repo) {
		this.emailSender = emailSender;
		this.userRepo = repo;
	}

	@GetMapping(path = "/hello")
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
	public ResponseEntity<?> passRecover(@RequestBody String correo) {
		String[] splitCorreo = correo.split(":");
		String email = splitCorreo[1].replaceAll("}", "");
		email = email.substring(1, email.length() - 1);

		this.token = UUID.randomUUID().toString();

		User usuario = userRepo.findByEmail(email);//falta control de existencia del correo
		this.mailPassChange = email;

		String url = "http://localhost:3000/reset/" + this.token;
		String bodyMessage = "Para cambiar la contraseña, click aqui: " + url;

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setFrom(mailRecovery);
		message.setSubject("Recuperación de contraeña para el usuario: " + usuario.getUserName());
		message.setText(bodyMessage);

		emailSender.send(message);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(path = "/check")
	public ResponseEntity<Boolean> checkToken(@RequestBody String appToken) {
		System.out.println("appToken: " + appToken);
		System.out.println("myToken " + this.token);
		Boolean sonIguales = false;
		if(appToken == this.token) {
			sonIguales = true;
		}
		return new ResponseEntity<Boolean>(sonIguales ,HttpStatus.OK);
	}

	@PatchMapping(path = "/passChange")
	public ResponseEntity<?> passChange(@RequestBody String newPass) {
		User usuario = userRepo.findByEmail(this.mailPassChange);
		usuario.setPassword(passwordEncoder.encode(newPass));
		userRepo.save(usuario);
		this.token = null;
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
