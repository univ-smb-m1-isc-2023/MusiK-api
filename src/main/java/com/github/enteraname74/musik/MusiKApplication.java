package com.github.enteraname74.musik;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MusiKApplication {
	public static void main(String[] args) {
		SpringApplication.run(MusiKApplication.class, args);
	}

//	@Bean
//	public Dotenv dotenv() {
//		return Dotenv.configure().directory(".").load();
//	}
}

