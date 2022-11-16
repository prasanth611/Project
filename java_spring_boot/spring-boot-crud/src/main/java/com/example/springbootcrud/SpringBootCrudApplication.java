package com.example.springbootcrud;

import com.example.springbootcrud.clients.Redis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCrudApplication.class, args);

		//setup redis client
		Redis.initializeSettings("127.0.0.1",6379, null, 5000, 5000);
	}

}
