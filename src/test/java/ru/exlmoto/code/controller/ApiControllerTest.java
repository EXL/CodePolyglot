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

package ru.exlmoto.code.controller;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ru.exlmoto.code.controller.helper.ControllerTestHelper;
import ru.exlmoto.code.helper.ResourceHelper;
import ru.exlmoto.code.service.DatabaseService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@AutoConfigureMockMvc
class ApiControllerTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ResourceHelper resource;

	@MockBean
	private DatabaseService database;

	private final ControllerTestHelper helper = new ControllerTestHelper();

	@Test
	public void testRaw() throws Exception {
		doReturn(Optional.empty()).when(database).getCodeSnippet(anyLong());

		helper.validatePlainTextUtf8(mvc, "/api/raw/100", "Error");

		helper.checkError4xx(mvc, "/api/raw");
		helper.checkError4xx(mvc, "/api/raw/100/unknown");
	}

	@Test
	public void testHighlight() throws Exception {
		helper.checkUnauthorizedPlainTextWithoutCsrf(mvc, "/api", "Error");

		String in = resource.readFileToString("classpath:/snippets/Java.snippet.txt");
		String out = resource.readFileToString("classpath:/expected/HighlightJs/Java.html.txt");
		helper.checkUnauthorizedPlainTextWithoutCsrfWithData(mvc, "/api?o=java", in, out);

		helper.checkError4xx(mvc, "/api/unknown");
	}

	@Test
	public void testCss() throws Exception {
		helper.validateCssUtf8(mvc, "/api/css", "/*");
		helper.validateCssUtf8(mvc, "/api/css?skin=techno", "/*");
		helper.validateCssUtf8(mvc, "/api/css?skin=techno&mode=HighlightRouge", "/*");
		helper.validateCssUtf8(mvc, "/api/css?skin=unknown&mode=HighlightRouge", "Error");
		helper.validateCssUtf8(mvc, "/api/css?skin=techno&mode=unknown", "Error");

		helper.checkError4xx(mvc, "/api/css/unknown");
	}

	@Test
	public void testVersions() throws Exception {
		helper.validateJsonUtf8(mvc, "/api/versions");

		helper.checkError4xx(mvc, "/api/versions/unknown");
	}
}
