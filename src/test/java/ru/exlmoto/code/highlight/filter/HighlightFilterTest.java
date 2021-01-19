/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020-2021 EXL <exlmotodev@gmail.com>
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

package ru.exlmoto.code.highlight.filter;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.exlmoto.code.helper.ResourceHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HighlightFilterTest {
	@Autowired
	private HighlightFilter filter;

	@Autowired
	private ResourceHelper resource;

	@Test
	public void testFilterCarriageReturn() {
		assertEquals("testtest", filter.filterCarriageReturn("test\rtest"));
		assertEquals("test\ntest", filter.filterCarriageReturn("test\n\rtest"));
		assertEquals("test\ntest", filter.filterCarriageReturn("test\r\ntest"));
		assertEquals("test\ntest\n", filter.filterCarriageReturn("test\r\ntest\r\n"));
		assertEquals("test\ntest\n", filter.filterCarriageReturn("test\r\ntest\n"));
		assertEquals("test\ntest", filter.filterCarriageReturn("test\r\ntest\r"));
	}

	@Test
	public void testTableCode() {
		String snippet = resource.readFileToString("classpath:/expected/SnippetFirstIn.html.txt");
		String tableCode = resource.readFileToString("classpath:/expected/TableCode.html.txt");
		String tableCodeHighlight = resource.readFileToString("classpath:/expected/TableCodeHighlight.html.txt");

		assertEquals(tableCode, filter.tableCode(snippet, 0L, 0L, false));
		assertEquals(tableCodeHighlight, filter.tableCode(snippet, 3L, 5L, false));
	}

	@Test
	public void testTableCodePlain() {
		String snippet = resource.readFileToString("classpath:/expected/SnippetFirstIn.html.txt");
		String tableCodePlain = resource.readFileToString("classpath:/expected/TableCodePlain.html.txt");
		String tableCodePlainHighlight =
			resource.readFileToString("classpath:/expected/TableCodePlainHighlight.html.txt");

		assertEquals(tableCodePlain, filter.tableCodePlain(snippet, 0L, 0L));
		assertEquals(tableCodePlainHighlight, filter.tableCodePlain(snippet, 3L, 5L));
	}

	@Test
	public void testSimpleCode() {
		String snippet = resource.readFileToString("classpath:/expected/SnippetFirstIn.html.txt");
		String simpleCode = resource.readFileToString("classpath:/expected/SimpleCode.html.txt");
		String simpleCodeHighlight = resource.readFileToString("classpath:/expected/SimpleCodeHighlight.html.txt");

		assertEquals(simpleCode, filter.simpleCode(snippet, 0L, 0L, false));
		assertEquals(simpleCodeHighlight, filter.simpleCode(snippet, 3L, 5L, false));
	}

	@Test
	public void testPlainCode() {
		String snippet = resource.readFileToString("classpath:/expected/SnippetFirstIn.html.txt");
		String plainCode = resource.readFileToString("classpath:/expected/PlainCode.html.txt");
		String plainCodeHighlight = resource.readFileToString("classpath:/expected/PlainCodeHighlight.html.txt");

		assertEquals(plainCode, filter.plainCode(snippet, 0L, 0L));
		assertEquals(plainCodeHighlight, filter.plainCode(snippet, 3L, 5L));
	}
}
