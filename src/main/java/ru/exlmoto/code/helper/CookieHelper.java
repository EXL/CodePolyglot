package ru.exlmoto.code.helper;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import ru.exlmoto.code.controller.enumeration.Lang;
import ru.exlmoto.code.controller.enumeration.Skin;
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
		return getCookieValue(request, LANG).map(Lang::checkLang).orElse(Lang.checkLang(DEFAULT_LANG)).name();
	}

	public String getSkin(HttpServletRequest request) {
		return getCookieValue(request, SKIN).map(Skin::checkSkin).orElse(Skin.checkSkin(DEFAULT_SKIN)).name();
	}

	public String getOptions(HttpServletRequest request) {
		return getCookieValue(request, OPTIONS).orElse(DEFAULT_OPTIONS);
	}

	public Mode getHighlight(HttpServletRequest request) {
		return getCookieValue(request, HIGHLIGHT).map(Mode::getMode).orElse(DEFAULT_HIGHLIGHT);
	}

	public void setOptions(HttpServletResponse response, String options) {
		if (StringUtils.hasText(options))
			response.addCookie(globalCookie(OPTIONS, options));
	}

	public void setHighlight(HttpServletResponse response, Mode highlight) {
		response.addCookie(globalCookie(HIGHLIGHT, highlight.name()));
	}

	public void setSkin(HttpServletResponse response, Skin skin) {
		response.addCookie(globalCookie(SKIN, skin.name()));
	}

	public void setLang(HttpServletResponse response, Lang lang) {
		response.addCookie(globalCookie(LANG, lang.name()));
	}

	private Optional<String> getCookieValue(HttpServletRequest request, String name) {
		return Optional.ofNullable(WebUtils.getCookie(request, name)).map(Cookie::getValue);
	}

	private Cookie globalCookie(String name, String value) {
		final Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		return cookie;
	}
}
