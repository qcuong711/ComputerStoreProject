package com.qcuong.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.qcuong.common.entity.Role;
import com.qcuong.common.entity.User;

@DataJpaTest
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
		User qcuong = new User("dangquangcuong711@gmail.com", "cuong71199", "Dang Quang", "Cuong");
		qcuong.addRole(roleAdmin);
		
		User savedUser = repo.save(qcuong);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUserTwoRoles() {
		User user2 = new User("user2@gmal.com", "user2", "User", "Num2");
		Role roleEmployee = new Role(2);
		Role roleShipper = new Role(3);
		
		user2.addRole(roleShipper);
		user2.addRole(roleEmployee);
		
		User savedUser = repo.save(user2);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUser() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User user = repo.findById(1).get();
		System.out.println(user);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetail() {
		User user = repo.findById(2).get();
		user.setEnabled(true);
		user.setEmail("changedEmail@method.com");
		
		repo.save(user);
	}
	
	@Test
	public void testChangeUserRole() {
		User user2 = repo.findById(2).get();
		Role roleShipper = new Role(3);
		
		user2.getRoles().remove(roleShipper);
		
		repo.save(user2);
	}
	
	@Test
	public void testDeleteUser() {
		Integer UserId = 2;
		repo.deleteById(UserId);

	}
	
}
