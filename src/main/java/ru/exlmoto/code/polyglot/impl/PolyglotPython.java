package ru.exlmoto.code.polyglot.impl;

import org.springframework.stereotype.Component;

import ru.exlmoto.code.polyglot.Language;
import ru.exlmoto.code.polyglot.Polyglot;

@Component
public class PolyglotPython extends Polyglot {
	@Override
	public String getLanguageVersion() {
		final String versionSnippet =
			"import platform" + "\n" +
			"\n" +
			"platform.python_version()";

		return execute(versionSnippet).orElse("Error");
	}

	@Override
	protected String language() {
		return Language.python.name();
	}
}
