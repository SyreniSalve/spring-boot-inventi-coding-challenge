package com.example.springbootinventicodingchallenge;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(basePackageClasses = { SpringBootInventiCodingChallengeApplication.class, Jsr310JpaConverters.class })
@SpringBootApplication
public class SpringBootInventiCodingChallengeApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootInventiCodingChallengeApplication.class, args);
	}

}
