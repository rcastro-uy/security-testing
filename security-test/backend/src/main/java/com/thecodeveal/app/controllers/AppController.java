package com.thecodeveal.app.controllers;

import com.thecodeveal.app.entities.User;
import com.thecodeveal.app.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/")
@CrossOrigin(origins = "http://localhost:3000")
public class AppController {

	private String tokenPassChange;

	private Long idPassChange;
	
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

	@PostMapping(path = "/passRecover")
	public ResponseEntity<?> passRecover(String correo) {
		//generar un token y guardarlo en la variable global
		//obtener el id del usuario perteneciente al correo y guardarlo en la variable global
		//enviar un mensaje al correo ingresado
		//el mensaje debe contener un URL, el cual debe contener el token
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PatchMapping(path = "/passChange")//no se como funciona patch
	public ResponseEntity<?> passChange(String newPass) {
		//reemplazlo la pass actual del usuario por la newPass(encriptada)
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
