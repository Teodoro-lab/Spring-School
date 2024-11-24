package com.teos.school.school_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class SchoolManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolManagementApplication.class, args);
	}

}
