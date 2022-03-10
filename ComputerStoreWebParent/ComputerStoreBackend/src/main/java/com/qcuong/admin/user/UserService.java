package com.qcuong.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.qcuong.common.entity.Role;
import com.qcuong.common.entity.User;

@Service
public class UserService {

	public static final int USER_PER_PAGE = 6;
	
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> listAll() {
		return (List<User>) userRepo.findAll();
	}
	
	public Page<User> listByPage(int pageNumber) {
		
		Pageable pageable = PageRequest.of(pageNumber - 1, USER_PER_PAGE);
		return userRepo.findAll(pageable);
		
	}
	
	public List<Role> listRoles() {
		return (List<Role>) roleRepo.findAll();
	}
	
	public void save(User user) {
		boolean isUpdatingUser = (user.getId() != null);
		
		if (isUpdatingUser) {
			User existUser = userRepo.findById(user.getId()).get();
			
			if (user.getPassword().isEmpty()) {
				user.setPassword(existUser.getPassword());
			} else {
				encodePassword(user);
			}
			
		} else {
			encodePassword(user);
		}
		
		userRepo.save(user);
	}
	
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepo.getUserByEmail(email);
		
		if (userByEmail == null) return true;
		
		boolean isCreatingNew = (id == null);
		
		if (isCreatingNew) {
			if (userByEmail != null) return false; 
		} else {
			if (userByEmail.getId() != id)	{
				return false;
			}
		}
		
		return true;
	}
	
	public User get(Integer id) throws UserNotFoundException  {
		try {
			return userRepo.findById(id).get();
		} catch (NoSuchElementException exception) {
			throw new UserNotFoundException("No user with ID " + id);
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepo.countById(id);
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("No user with ID " + id);
		}
		
		userRepo.deleteById(id);
	}
	
}
