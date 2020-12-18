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
