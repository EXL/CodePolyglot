package ru.exlmoto.code.helper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ResourceHelperTest {
	@Autowired
	private ResourceHelper resource;

	@Test
	public void testReadFileToString() {
		String res = resource.readFileToString("classpath:/static/robots.txt");
		assertThat(res).isNotBlank();
		System.out.println(res);

		assertThrows(UncheckedIOException.class, () -> resource.readFileToString("classpath:unknown-file.ext"));
	}
}
