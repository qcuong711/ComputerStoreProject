package com.qcuong.admin.user;

import org.springframework.data.repository.CrudRepository;

import com.qcuong.common.entity.User;

public interface UserRepository extends CrudRepository<User, Integer>{
	
}
