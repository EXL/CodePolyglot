package ru.exlmoto.code.highlight.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LanguageUnitTest {
	@Test
	public void testNames() {
		String[] names = Language.names();
		for (String name : names) {
			assertEquals(name, Language.valueOf(name).name());
		}
	}
}
