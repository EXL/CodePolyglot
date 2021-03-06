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

package ru.exlmoto.code.highlight.filter;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.exlmoto.code.helper.ResourceHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class TagCompensatorTest {
	@Autowired
	private TagCompensator compensator;

	@Autowired
	private ResourceHelper resource;

	@Test
	public void testCompensateTags() {
		String snippetFirstIn = resource.readFileToString("classpath:/expected/SnippetFirstIn.html.txt");
		String snippetFirstOut = resource.readFileToString("classpath:/expected/SnippetFirstOut.html.txt");
		String snippetSecondIn = resource.readFileToString("classpath:/expected/SnippetSecondIn.html.txt");
		String snippetSecondOut = resource.readFileToString("classpath:/expected/SnippetSecondOut.html.txt");

		compensator.compensateTags(snippetFirstIn).ifPresent(renderedHtml ->
			assertEquals(snippetFirstOut, renderedHtml));
		compensator.compensateTags(snippetSecondIn).ifPresent(renderedHtml ->
			assertEquals(snippetSecondOut, renderedHtml));
	}

	@Test
	public void testCompensateTagsErrors() {
		assertFalse(compensator.compensateTags(null).isPresent());
		assertFalse(compensator.compensateTags("").isPresent());
		assertFalse(compensator.compensateTags(" ").isPresent());
	}
}
