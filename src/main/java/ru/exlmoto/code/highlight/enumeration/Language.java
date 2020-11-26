package ru.exlmoto.code.highlight.enumeration;

public enum Language {
	js,
	python,
	ruby;

	public static String[] names() {
		Language[] languages = values();
		String[] languageNames = new String[languages.length];
		for (int i = 0; i < languages.length; ++i)
			languageNames[i] = languages[i].name();
		return languageNames;
	}
}
