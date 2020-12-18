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

package ru.exlmoto.code.highlight.implementation;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HighlightPygmentsTest {
	@Autowired
	private HighlightPygments pygments;

	@Test
	public void testInjectChunkToLastLineStart() {
		final String renderLanguageSnippetIn =
//			"source = \"\"\"\n" + StringUtils.escapeJava(code) + "\n\"\"\"" + "\n" +
			"formatter = HtmlFormatter(nowrap=True)" + "\n" +
			"lexer = get_lexer_by_name('" + "java" + "')" + "\n" +
			"\n" +
			"highlight(source, lexer, formatter)";

		final String renderLanguageSnippetOut =
//			"source = \"\"\"\n" + StringUtils.escapeJava(code) + "\n\"\"\"" + "\n" +
			"formatter = HtmlFormatter(nowrap=True)" + "\n" +
			"lexer = get_lexer_by_name('" + "java" + "')" + "\n" +
			"\n" +
			"result = highlight(source, lexer, formatter)";

		assertEquals(renderLanguageSnippetIn,
			pygments.injectChunkToLastLineStart(renderLanguageSnippetIn, null));
		assertEquals(renderLanguageSnippetOut,
			pygments.injectChunkToLastLineStart(renderLanguageSnippetIn, "result = "));
	}
}
