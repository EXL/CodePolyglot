package ru.exlmoto.code.controller.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LangUnitTest {
	@Test
	public void checkLang() {
		assertEquals(Lang.ru, Lang.checkLang(null));
		assertEquals(Lang.ru, Lang.checkLang(""));
		assertEquals(Lang.ru, Lang.checkLang(" "));
		assertEquals(Lang.ru, Lang.checkLang("unknown"));
		assertEquals(Lang.ru, Lang.checkLang("ru"));
		assertEquals(Lang.en, Lang.checkLang("en"));
	}
}
