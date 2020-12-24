/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 EXL <exlmotodev@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
//		assertEquals(Mode.HighlightPygments, Mode.getMode("HighlightPygments"));
//		assertEquals(Mode.HighlightPygmentsJython, Mode.getMode("HighlightPygmentsJython"));
		assertEquals(Mode.HighlightRouge, Mode.getMode("HighlightRouge"));
	}

	@Test
	public void testGetCss() {
		assertEquals("static/css/techno/hjs.css", Mode.getCss(Mode.HighlightJs, Skin.techno));
//		assertEquals("static/css/techno/merged.css", Mode.getCss(Mode.HighlightPygments, Skin.techno));
//		assertEquals("static/css/techno/merged.css", Mode.getCss(Mode.HighlightPygmentsJython, Skin.techno));
		assertEquals("static/css/techno/merged.css", Mode.getCss(Mode.HighlightRouge, Skin.techno));
		assertEquals("static/css/pastorg/hjs.css", Mode.getCss(Mode.HighlightJs, Skin.pastorg));
//		assertEquals("static/css/pastorg/merged.css", Mode.getCss(Mode.HighlightPygments, Skin.pastorg));
//		assertEquals("static/css/pastorg/merged.css", Mode.getCss(Mode.HighlightPygmentsJython, Skin.pastorg));
		assertEquals("static/css/pastorg/merged.css", Mode.getCss(Mode.HighlightRouge, Skin.pastorg));
	}
}
