package com.qcuong.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.qcuong.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

	@Autowired
	private RoleRepository repo;

	//add table roles and role admin
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "manage everything");
		Role savedRole = repo.save(roleAdmin);

		assertThat(savedRole.getId()).isGreaterThan(0);
	}

	//add others roles
	@Test
	public void testCreateOthersRole() {
		Role roleEmployee = new Role("Employee",
				"manage category, brand, customer, product, price, shipping, order, newspaper, report, question, review");
		Role roleShipper = new Role("Shipper", "view orders and update order status");
		
		repo.saveAll(List.of(roleEmployee, roleShipper));
	}
}
