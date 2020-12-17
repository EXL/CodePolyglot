package ru.exlmoto.code.highlight.enumeration;

import org.junit.jupiter.api.Test;

import ru.exlmoto.code.controller.enumeration.Skin;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModeUnitTest {
	@Test
	public void testGetMode() {
		assertEquals(Mode.HighlightJs, Mode.getMode(null));
		assertEquals(Mode.HighlightJs, Mode.getMode(""));
		assertEquals(Mode.HighlightJs, Mode.getMode(" "));
		assertEquals(Mode.HighlightJs, Mode.getMode("unknown"));
		assertEquals(Mode.HighlightJs, Mode.getMode("HighlightJs"));
		assertEquals(Mode.HighlightPygments, Mode.getMode("HighlightPygments"));
//		assertEquals(Mode.HighlightPygmentsJython, Mode.getMode("HighlightPygmentsJython"));
		assertEquals(Mode.HighlightRouge, Mode.getMode("HighlightRouge"));
	}

	@Test
	public void testGetCss() {
		assertEquals("static/css/techno/hjs.css", Mode.getCss(Mode.HighlightJs, Skin.techno));
		assertEquals("static/css/techno/merged.css", Mode.getCss(Mode.HighlightPygments, Skin.techno));
//		assertEquals("static/css/techno/merged.css", Mode.getCss(Mode.HighlightPygmentsJython, Skin.techno));
		assertEquals("static/css/techno/merged.css", Mode.getCss(Mode.HighlightRouge, Skin.techno));
		assertEquals("static/css/pastorg/hjs.css", Mode.getCss(Mode.HighlightJs, Skin.pastorg));
		assertEquals("static/css/pastorg/merged.css", Mode.getCss(Mode.HighlightPygments, Skin.pastorg));
//		assertEquals("static/css/pastorg/merged.css", Mode.getCss(Mode.HighlightPygmentsJython, Skin.pastorg));
		assertEquals("static/css/pastorg/merged.css", Mode.getCss(Mode.HighlightRouge, Skin.pastorg));
	}
}
