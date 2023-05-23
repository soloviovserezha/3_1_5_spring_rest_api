package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@SpringBootApplication
public class SpringBootSecurityDemoApplication implements ApplicationRunner {

	private final UserService userService;
	private final RoleService roleService;

	@Autowired
	public SpringBootSecurityDemoApplication(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}


	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		Role roleUser = roleService.addRole(new Role("ROLE_USER"));
		Role roleAdmin = roleService.addRole(new Role("ROLE_ADMIN"));

		User user = new User();

		user.setUsername("user");
		user.setEmail("user@gmail.com");
		user.setPassword("user");
		user.setRoles(Set.of(roleUser));

		User admin = new User();

		admin.setUsername("admin");
		admin.setEmail("admin@gmail.com");
		admin.setPassword("admin");
		admin.setRoles(Set.of(roleUser, roleAdmin));

		userService.saveUser(user);
		userService.saveUser(admin);

		System.out.println("GET USER: " + userService.getUserList().get(0));
		System.out.println("GET ADMIN: " + userService.getUserList().get(1));
	}
}
