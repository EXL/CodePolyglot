package ru.exlmoto.code.polyglot.impl;

import org.springframework.stereotype.Component;

import ru.exlmoto.code.polyglot.Language;
import ru.exlmoto.code.polyglot.Polyglot;

@Component
public class PolyglotRuby extends Polyglot {
	@Override
	protected String executeAux(String sourceCode) {
		return polyglot.eval(language(), sourceCode).asString();
	}

	@Override
	public String getLanguageVersion() {
		final String versionSnippet =
			"RUBY_VERSION";

		return execute(versionSnippet).orElse("Error");
	}

	@Override
	protected String language() {
		return Language.ruby.name();
	}
}
