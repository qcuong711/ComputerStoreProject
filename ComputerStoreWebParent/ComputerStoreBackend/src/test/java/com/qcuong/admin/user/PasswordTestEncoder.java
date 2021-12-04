package com.qcuong.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTestEncoder {
	
	@Test
	public void testEncodePassword() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "cuong71199";
		String encodePassword = passwordEncoder.encode(rawPassword);
		
		System.out.println(encodePassword);
		
		boolean matches = passwordEncoder.matches(rawPassword, encodePassword);
		
		assertThat(matches).isTrue();
	}

}
