package ru.exlmoto.code.controller.enumeration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InfoUnitTest {
	@Test
	public void testGetDescription() {
		assertEquals("code.info.unknown", Info.getDescription(null));
		assertEquals("code.info.unknown", Info.getDescription(""));
		assertEquals("code.info.unknown", Info.getDescription(" "));
		assertEquals("code.info.unknown", Info.getDescription("unknown"));
		assertEquals("code.info.empty", Info.getDescription("empty"));
		assertEquals("code.info.database", Info.getDescription("databaseError"));
		assertEquals("code.info.delete.ok", Info.getDescription("deleteOk"));
		assertEquals("code.info.delete.error", Info.getDescription("deleteError"));
	}
}
