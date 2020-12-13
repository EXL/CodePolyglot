package ru.exlmoto.code.controller.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SkinUnitTest {
	@Test
	public void testCheckSkin() {
		assertEquals(Skin.techno, Skin.checkSkin(null));
		assertEquals(Skin.techno, Skin.checkSkin(""));
		assertEquals(Skin.techno, Skin.checkSkin(" "));
		assertEquals(Skin.techno, Skin.checkSkin("unknown"));
		assertEquals(Skin.techno, Skin.checkSkin("techno"));
		assertEquals(Skin.pastorg, Skin.checkSkin("pastorg"));
	}
}
