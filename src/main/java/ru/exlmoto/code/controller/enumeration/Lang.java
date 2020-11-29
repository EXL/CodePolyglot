package ru.exlmoto.code.controller.enumeration;

public enum Lang {
	ru,
	en;

	public static Lang checkLang(String lang) {
		try {
			return Lang.valueOf(lang);
		} catch (IllegalArgumentException ignored) {
			return ru;
		}
	}
}
