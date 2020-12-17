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

package ru.exlmoto.code.highlight;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import ru.exlmoto.code.helper.ResourceHelper;
import ru.exlmoto.code.highlight.enumeration.Mode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class HighlightServiceTest {
	@Autowired
	private HighlightService highlight;

	@Autowired
	private ResourceHelper resource;

	@Test
	public void testHighlightCode() {
		testSnippets("Kotlin", "kotlin");
		testSnippets("Java", "java");
		testSnippets("Css", "css");
		testSnippets("Js", "js");
		testSnippets("Html", "html");
		testSnippets("Python", "python");
		testSnippets("Diff", "diff");
	}

	@Test
	public void testApplicationVersions() {
		assertTrue(checkVersion(highlight.getApplicationVersions().getFirst()));
		String[] versionChunks = highlight.getApplicationVersions().getSecond().split(", ");
		assertTrue(checkVersion(versionChunks[0]));
	}

	@Test
	public void testGetLibraryVersions() {
		highlight.getLibraryVersions().forEach((key, value) -> {
			assertTrue(checkVersion(value.getFirst()));
			assertTrue(checkVersion(value.getSecond()));
		});
	}

	private void testSnippets(String filename, String lang) {
		String snippet =
			resource.readFileToString("classpath:/snippets/" + filename + ".snippet.txt");
		String htmlHighlightJs =
			resource.readFileToString("classpath:/expected/HighlightJs/" + filename + ".html.txt");
		String htmlRouge =
			resource.readFileToString("classpath:/expected/Rouge/" + filename + ".html.txt");
//		String htmlPygments =
//			resource.readFileToString("classpath:/expected/Pygments/" + filename + ".html.txt");
//		String htmlPygmentsJython =
//			resource.readFileToString("classpath:/expected/PygmentsJython/" + filename + ".html.txt");

		assertEquals(htmlHighlightJs, highlight.highlightCode(Mode.HighlightJs, lang, snippet));
		assertEquals(htmlRouge, highlight.highlightCode(Mode.HighlightRouge, lang, snippet));
//		assertEquals(htmlPygments, highlight.highlightCode(Mode.HighlightPygments, lang, snippet));
//		assertEquals(htmlPygmentsJython, highlight.highlightCode(Mode.HighlightPygmentsJython, lang, snippet));
	}

	private boolean checkVersion(String version) {
		String[] chunks = version.split("\\.");
		for (String chunk : chunks) {
			if (!isNumber(chunk)) {
				return false;
			}
		}
		return true;
	}

	public boolean isNumber(String chunk) {
		if (StringUtils.hasText(chunk)) {
			try {
				Integer.parseInt(chunk);
				return true;
			} catch (NumberFormatException ignored) { }
		}
		return false;
	}
}
