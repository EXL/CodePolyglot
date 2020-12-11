package ru.exlmoto.code.controller.enumeration;

import org.springframework.util.StringUtils;

public enum Lang {
	ru,
	en;

	public static Lang checkLang(String lang) {
		if (StringUtils.hasText(lang)) {
			try {
				return Lang.valueOf(lang);
			} catch (IllegalArgumentException ignored) { }
		}
		return ru;
	}
}
