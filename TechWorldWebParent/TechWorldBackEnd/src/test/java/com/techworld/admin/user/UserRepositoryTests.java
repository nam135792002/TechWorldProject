package com.techworld.admin.user;

import com.techworld.common.entity.Role;
import com.techworld.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository repo;

	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userNam = new User("phuongnama32112002@gmail.com","123456789","Nam","Nguyen Phuong","0334069054");
		userNam.setRole(roleAdmin);

		User savedUser = repo.save(userNam);

		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateNewUser() {
		User userThanh = new User("thanhking@gmail.com","123456789","Thanh","Le Tu","0934958717");
		Role roleEditor = new Role(3);
		userThanh.setRole(roleEditor);

		User savedUser = repo.save(userThanh);

		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateNewUser2() {
		User userThanh = new User("congchat.03@gmail.com","123456789","Chat","Van Cong","0762717984");
		Role roleEditor = new Role(3);
		userThanh.setRole(roleEditor);

		User savedUser = repo.save(userThanh);

		assertThat(savedUser.getId()).isGreaterThan(0);
	}
//
	@Test
	public void testListAllUser(){
		Iterable<User> listUser = repo.findAll();
		listUser.forEach(System.out::println);
	}
//
	@Test
	public void testGetUserById(){
		User userNam = repo.findById(1).get();
		System.out.println(userNam);
		assertThat(userNam).isNotNull();
	}
//
	@Test
	public  void testUpdateUser(){
		User userNam = repo.findById(1).get();
		userNam.setEnabled(true);
		userNam.setEmail("namphuong@gmail.com");

		repo.save(userNam);
	}
//
	@Test
	public void testUpdateUserRoles(){
		User userThanh = repo.findById(4).get();
		Role roleSalesPerson = new Role(2);

		userThanh.setRole(roleSalesPerson);

		repo.save(userThanh);
	}
//
	@Test
	public void testDeleteUser(){
		Integer userId = 2;
		repo.deleteById(userId);

	}
//
	@Test
	public void testGetUserByEmail(){
		String email = "namphuong@gmail.com";
		User user = repo.getUserByEmail(email);

		assertThat(user).isNotNull();
	}
//
	@Test
	public void testCountById(){
		Integer id = 1;
		Long countById = repo.countById(id);
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
//
	@Test
	public void testDisableUser(){
		Integer id = 1;
		repo.updateEnabledStatus(id,false);
	}
//
	@Test
	public void testEnableUser(){
		Integer id = 1;
		repo.updateEnabledStatus(id,true);
	}

	@Test
	public void testList(){
		List<User> listUser = repo.listUserWithoutUserId(42);
		listUser.forEach(System.out::println);
	}
}
