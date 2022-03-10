package com.qcuong.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.qcuong.common.entity", "com.qcuong.admin.user"})

// main entry point of Spring boot project
public class ComputerStoreBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComputerStoreBackendApplication.class, args);
	}

}
