package ru.exlmoto.code.helper;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import ru.exlmoto.code.highlight.enumeration.Mode;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

@Component
public class CookieHelper {
	public static final String LANG              = "lang";
	public static final String SKIN              = "skin";
	public static final String OPTIONS           = "options";
	public static final String HIGHLIGHT         = "highlight";

	public static final String DEFAULT_LANG      = "ru";
	public static final String DEFAULT_SKIN      = "techno";
	public static final String DEFAULT_OPTIONS   = "java";
	public static final Mode DEFAULT_HIGHLIGHT   = Mode.HighlightJs;

	public String getLang(HttpServletRequest request) {
		return getCookieValue(request, LANG).orElse(DEFAULT_LANG);
	}

	public String getSkin(HttpServletRequest request) {
		return getCookieValue(request, SKIN).orElse(DEFAULT_SKIN);
	}

	public String getOptions(HttpServletRequest request) {
		return getCookieValue(request, OPTIONS).orElse(DEFAULT_OPTIONS);
	}

	public Mode getHighlight(HttpServletRequest request) {
		return getCookieValue(request, HIGHLIGHT).map(Mode::getMode).orElse(DEFAULT_HIGHLIGHT);
	}

	public void setOptions(HttpServletResponse response, String options) {
		if (StringUtils.hasText(options))
			response.addCookie(new Cookie(OPTIONS, options));
	}

	public void setHighlight(HttpServletResponse response, Mode highlight) {
		response.addCookie(new Cookie(HIGHLIGHT, highlight.name()));
	}

	public void setSkin(HttpServletResponse response, String skin) {
		response.addCookie(new Cookie(SKIN, skin));
	}

	public void setLang(HttpServletResponse response, String lang) {
		response.addCookie(new Cookie(LANG, lang));
	}

	private Optional<String> getCookieValue(HttpServletRequest request, String name) {
		return Optional.ofNullable(WebUtils.getCookie(request, name)).map(Cookie::getValue);
	}
}
