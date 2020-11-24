package ru.exlmoto.code;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CodeServiceApplicationTests {
	@Autowired
	private CodeServiceApplication codeServiceApplication;

	@Test
	void contextLoads() {
		assertNotNull(codeServiceApplication);
	}
}
