package ru.exlmoto.code.controller.helper;

import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.Matchers.endsWith;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

public class ControllerTestHelper {
	public void validatePlainTextUtf8(MockMvc mvc, String path, String contains) throws Exception {
		mvc.perform(get(path).contentType(MediaType.TEXT_PLAIN))
			.andDo(print())
			.andExpect(header().string("content-type", containsString("text/plain;charset=UTF-8")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString(contains)));
	}

	public void validateCssUtf8(MockMvc mvc, String path, String contains) throws Exception {
		mvc.perform(get(path))
			.andDo(print())
			.andExpect(header().string("content-type", containsString("text/css;charset=UTF-8")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString(contains)));
	}

	public void validateJsonUtf8(MockMvc mvc, String path) throws Exception {
		mvc.perform(get(path).contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(header().string("content-type", containsString("application/json;charset=UTF-8")))
			.andExpect(content().string(startsWith("{")))
			.andExpect(content().string(endsWith("}")));
	}

	public void validateHtmlUtf8(MockMvc mvc, String path, String contains) throws Exception {
		mvc.perform(get(path).contentType(MediaType.TEXT_HTML))
			.andDo(print())
			.andExpect(header().string("content-type", containsString("text/html;charset=UTF-8")))
			.andExpect(status().isOk())
			.andExpect(content().string(startsWith("<!")))
			.andExpect(content().string(endsWith(">\n")))
			.andExpect(content().string(containsString(contains)));
	}

	public void checkError4xx(MockMvc mvc, String path) throws Exception {
		mvc.perform(get(path).contentType(MediaType.TEXT_HTML))
			.andDo(print())
			.andExpect(status().is4xxClientError());
	}

	public void checkAuthorizedWithoutCsrf(MockMvc mvc, String path) throws Exception {
		mvc.perform(post(path).contentType(MediaType.TEXT_HTML)
			.param("username", "admin")
			.param("password", "password")
			.with(authorizedUser()))
			.andDo(print())
			.andExpect(status().isForbidden());
	}

	public void checkAuthorizedWithCsrfRedirect(MockMvc mvc, String path, String redirectPattern) throws Exception {
		checkAuthorizedWithCsrfRedirectParam(mvc, path, redirectPattern,
			"username", "admin", "password", "password");
	}

	public void checkAuthorizedWithCsrfRedirectParam(MockMvc mvc, String path, String redirectPattern,
	                                                 String paramFirst, String valueFirst,
	                                                 String paramSecond, String valueSecond) throws Exception {
		authorizedAux(mvc, path, redirectPattern, paramFirst, valueFirst, paramSecond, valueSecond, authorizedUser());
	}

	public void checkUnauthorizedPlainTextWithoutCsrf(MockMvc mvc, String path, String contains) throws Exception {
		mvc.perform(post(path).contentType(MediaType.TEXT_PLAIN))
			.andDo(print())
			.andExpect(header().string("content-type", containsString("text/plain;charset=UTF-8")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString(contains)));
	}

	public void checkUnauthorizedPlainTextWithoutCsrfWithData(MockMvc mvc, String path,
	                                                          String in, String out) throws Exception {
		mvc.perform(post(path).contentType(MediaType.TEXT_PLAIN).content(in))
			.andDo(print())
			.andExpect(header().string("content-type", containsString("text/plain;charset=UTF-8")))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo(out)));
	}

	public void checkRedirect(MockMvc mvc, String path, String redirectPattern) throws Exception {
		mvc.perform(get(path).contentType(MediaType.TEXT_HTML))
			.andDo(print())
			.andExpect(redirectedUrlPattern(redirectPattern))
			.andExpect(status().isFound());
	}

	public void checkRedirectAndCookie(MockMvc mvc, String path, String redirectPattern,
	                                   String cookieName, String cookieValue) throws Exception {
		mvc.perform(get(path).contentType(MediaType.TEXT_HTML))
			.andExpect(cookie().value(cookieName, cookieValue))
			.andDo(print())
			.andExpect(redirectedUrlPattern(redirectPattern))
			.andExpect(status().isFound());
	}

	private void authorizedAux(MockMvc mvc, String path, String redirectPattern,
	                           String paramFirst, String valueFirst, String paramSecond, String valueSecond,
	                           RequestPostProcessor user) throws Exception {
		mvc.perform(post(path).contentType(MediaType.TEXT_HTML)
			.param(paramFirst, valueFirst)
			.param(paramSecond, valueSecond)
			.with(user)
			.with(csrf()))
			.andDo(print())
			.andExpect(redirectedUrlPattern(redirectPattern))
			.andExpect(status().isFound());
	}

	private RequestPostProcessor authorizedUser() {
		return user("admin").password("password")
			.authorities(new SimpleGrantedAuthority("Owner"));
	}
}
