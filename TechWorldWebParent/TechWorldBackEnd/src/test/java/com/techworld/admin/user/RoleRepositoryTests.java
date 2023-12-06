package com.techworld.admin.user;

import com.techworld.admin.user.RoleRepository;
import com.techworld.common.entity.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {
	
	@Autowired
	private RoleRepository repo;
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin","manage everything");
		Role saveRole = repo.save(roleAdmin);
		Assertions.assertThat(saveRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRoles() {
		Role roleSalesperson = new Role("Salesperson","manage product price, "
			+ "customers, shipping, orders and sales report");
		Role roleEditor = new Role("Editor","manage categories, brands, "
			+ "products, articles and menus");

		try {
	        repo.saveAll(List.of(roleSalesperson, roleEditor));
	    } catch (Exception e) {
	        // Log or handle the exception as needed
	        e.printStackTrace();
	    }
	}
}