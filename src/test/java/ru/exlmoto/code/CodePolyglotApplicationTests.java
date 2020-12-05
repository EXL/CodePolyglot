package ru.exlmoto.code;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CodePolyglotApplicationTests {
	@Autowired
	private CodePolyglotApplication codePolyglotApplication;

	@Test
	void contextLoads() {
		assertNotNull(codePolyglotApplication);
	}
}
