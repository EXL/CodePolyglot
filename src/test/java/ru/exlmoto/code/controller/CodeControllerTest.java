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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ru.exlmoto.code.controller.helper.ControllerTestHelper;
import ru.exlmoto.code.service.DatabaseService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@AutoConfigureMockMvc
class CodeControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private DatabaseService database;

	private final ControllerTestHelper helper = new ControllerTestHelper();

	@Test
	public void testIndex() throws Exception {
		helper.validateHtmlUtf8(mvc, "/", "!DOCTYPE");
		helper.validateHtmlUtf8(mvc, "/unknown", "!DOCTYPE");
		helper.validateHtmlUtf8(mvc, "/unknown/", "!DOCTYPE");
		helper.validateHtmlUtf8(mvc, "/1", "!DOCTYPE");
		helper.validateHtmlUtf8(mvc, "/1/", "!DOCTYPE");

		helper.checkError4xx(mvc, "/unknown/unknown");

		try {
			helper.validateHtmlUtf8(mvc, "/", "/logout");
		} catch (AssertionError ignored) {
			/* There is no "/logout" link on unauthorized index page. */
		}
	}

	@Test
	@WithMockUser
	public void testIndexWithMockUser() throws Exception {
		helper.validateHtmlUtf8(mvc, "/", "/logout");
	}

	@Test
	public void testEdit() throws Exception {
		doReturn(Optional.of(100L)).when(database).saveCodeSnippet(any());

		helper.validateHtmlUtf8(mvc, "/edit", "!DOCTYPE");

		helper.checkAuthorizedWithoutCsrf(mvc, "/edit");
		helper.checkAuthorizedWithCsrfRedirect(mvc, "/edit", "/**");
	}

	@Test
	public void testDelete() throws Exception {
		helper.checkRedirect(mvc, "/delete", "**/login");
		helper.checkRedirect(mvc, "/delete/100/", "**/login");
		helper.checkRedirect(mvc, "/delete/100/unknown", "**/login");
	}

	@Test
	@WithMockUser
	public void testDeleteWithMockUser() throws Exception {
		doReturn(true).when(database).deleteCodeSnippet(anyLong());

		helper.validateHtmlUtf8(mvc, "/delete", "!DOCTYPE");

		helper.checkRedirect(mvc, "/delete/100/", "/**");

		helper.checkError4xx(mvc, "/delete/100/unknown");
	}

	@Test
	public void testOpts() throws Exception {
		helper.checkRedirect(mvc, "/opts", "/**");

		helper.checkRedirectAndCookie(mvc, "/opts?lang=ru", "/**", "lang", "ru");
		helper.checkRedirectAndCookie(mvc, "/opts?skin=techno", "/**", "skin", "techno");
		helper.checkRedirectAndCookie(mvc, "/opts?lang=ru&skin=techno", "/**", "lang", "ru");
		helper.checkRedirectAndCookie(mvc, "/opts?lang=ru&skin=techno", "/**", "skin", "techno");
		helper.checkRedirectAndCookie(mvc, "/opts?lang=en&skin=pastorg", "/**", "lang", "en");
		helper.checkRedirectAndCookie(mvc, "/opts?lang=en&skin=pastorg", "/**", "skin", "pastorg");
		helper.checkRedirectAndCookie(mvc, "/opts?lang=unknown&skin=unknown", "/**", "lang", "ru");
		helper.checkRedirectAndCookie(mvc, "/opts?lang=unknown&skin=unknown", "/**", "skin", "techno");

		helper.checkError4xx(mvc, "/opts/unknown");
	}
}
