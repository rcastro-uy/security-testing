package com.thecodeveal.app;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.thecodeveal.app.entities.Authority;
import com.thecodeveal.app.entities.User;
import com.thecodeveal.app.repository.UserDetailsRepository;

import static com.thecodeveal.app.entities.UserRole.*;

@SpringBootApplication
public class SpringSecurityDemoAppApplication {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDemoAppApplication.class, args);
	}
	
	@PostConstruct
	protected void init() {
		
//		List<Authority> authorityListUser=new ArrayList<>();
//		List<Authority> authorityListAdmin=new ArrayList<>();
//
//		authorityListUser.add(createAuthority("USER","User role"));
//		authorityListAdmin.add(createAuthority("ADMIN","Admin role"));
		
		User user=new User();
		
		user.setUserName("manuelbiurrun");
		user.setFirstName("Manuel");
		user.setLastName("Biurrun");
		
		user.setPassword(passwordEncoder.encode("montxito"));
		user.setEnabled(true);
		user.setAuthorities(USER.getGrantedAuthorities());

		userDetailsRepository.save(user);
//---------------------------------------------------------------------------
		User userAdmin=new User();

		userAdmin.setUserName("rodrigocastro");
		userAdmin.setFirstName("Rodrigo");
		userAdmin.setLastName("Castro");

		userAdmin.setPassword(passwordEncoder.encode("javaSucks"));
		userAdmin.setEnabled(true);
		userAdmin.setAuthorities(ADMIN.getGrantedAuthorities());
		
		userDetailsRepository.save(userAdmin);
		
		
		
	}
	
	
//	private Authority createAuthority(String roleCode,String roleDescription) {
//		Authority authority=new Authority();
//		authority.setRoleCode(roleCode);
//		authority.setRoleDescription(roleDescription);
//		return authority;
//	}
	
	

}
