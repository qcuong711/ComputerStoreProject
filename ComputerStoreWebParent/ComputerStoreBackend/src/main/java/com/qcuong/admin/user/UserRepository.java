package com.qcuong.admin.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.qcuong.common.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	@Query("SELECT u FROM User u Where u.email = :email")
	public User getUserByEmail(@Param("email") String email);
}
