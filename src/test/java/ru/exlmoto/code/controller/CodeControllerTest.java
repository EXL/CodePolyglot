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
