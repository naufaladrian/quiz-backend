package com.app.quizz;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJdbcAuditing
@SpringBootApplication
public class QuizzApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(QuizzApplication.class, args);
	}

}
