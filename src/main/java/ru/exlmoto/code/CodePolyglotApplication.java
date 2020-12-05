package ru.exlmoto.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = CodePolyglotApplication.class)
public class CodePolyglotApplication {
	public static void main(String[] args) {
		SpringApplication.run(CodePolyglotApplication.class, args);
	}
}
