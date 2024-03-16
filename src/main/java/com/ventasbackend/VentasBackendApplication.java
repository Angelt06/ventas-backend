package com.ventasbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class VentasBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(VentasBackendApplication.class, args);
	}

	/*
	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner createPasswordEncoder(){
		return  args -> {
			System.out.println(passwordEncoder.encode("clave123"));
		};
	}
	*/

}
