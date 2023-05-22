package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@SpringBootApplication
public class SpringBootSecurityDemoApplication implements ApplicationRunner {

	private final UserService userService;

	@Autowired
	public SpringBootSecurityDemoApplication(UserService userService) {
		this.userService = userService;
	}


	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		Set<Role> rolesSet = new LinkedHashSet<>();
		rolesSet.add(new Role("ROLE_USER"));
		rolesSet.add(new Role("ROLE_ADMIN"));

		User user = new User();

		Set<Role> userRoles = new HashSet<>();
		userRoles.add(rolesSet.stream().findFirst().get());

		user.setUsername("user");
		user.setEmail("user@gmail.com");
		user.setPassword("user");
		user.setRoles(userRoles);

		User admin = new User();

		admin.setUsername("admin");
		admin.setEmail("admin@gmail.com");
		admin.setPassword("admin");
		admin.setRoles(rolesSet);

		userService.saveUser(user);
		userService.saveUser(admin);

		System.out.println("GET USER: " + userService.getUserList().get(0));
		System.out.println("GET ADMIN: " + userService.getUserList().get(1));
	}
}
