package ru.exlmoto.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = CodeServiceApplication.class)
public class CodeServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CodeServiceApplication.class, args);
	}
}
