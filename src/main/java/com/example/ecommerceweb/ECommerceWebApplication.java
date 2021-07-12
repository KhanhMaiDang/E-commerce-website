package com.example.ecommerceweb;

import com.example.ecommerceweb.model.Role;
import com.example.ecommerceweb.model.User;
import com.example.ecommerceweb.repository.RoleRepository;
import com.example.ecommerceweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
//@EnableGlobalMethodSecurity
public class ECommerceWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceWebApplication.class, args);
	}

}
//public class ECommerceWebApplication implements CommandLineRunner {
//
//	public static void main(String[] args) {
//		SpringApplication.run(ECommerceWebApplication.class, args);
//	}
//
//	@Autowired
//	UserRepository userRepository;
//	@Autowired
//	PasswordEncoder passwordEncoder;
//	@Autowired
//	RoleRepository roleRepository;
//
//	@Override
//	public void run(String... args) throws Exception {
//		// Khi chương trình chạy
//		// Insert vào csdl một user.
//		User user = new User();
//		user.setUsername("maii");
//		user.setPassword(passwordEncoder.encode("maii"));
//		Role role = new Role("USER");
//		user.getRoles().add(role);
//		role.getUsers().add(user);
//		System.out.println(role);
//		System.out.println(user);
//		userRepository.save(user);
//	}
//
//}
