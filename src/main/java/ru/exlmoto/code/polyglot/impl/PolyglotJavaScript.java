package ru.exlmoto.code.polyglot.impl;

import org.springframework.stereotype.Component;

import ru.exlmoto.code.polyglot.Language;
import ru.exlmoto.code.polyglot.Polyglot;

@Component
public class PolyglotJavaScript extends Polyglot {
	@Override
	protected String executeAux(String sourceCode) {
		return polyglot.eval(language(), sourceCode).asString();
	}

	@Override
	public String getLanguageVersion() {
		/*
		 * TODO:
		 *  Change `Graal.versionJS` to `Graal.versionECMAScript` if this variable appears in the next release.
		 * Additional information:
		 *  https://github.com/graalvm/graaljs/issues/378
		 */
		final String versionSnippet =
			"Graal.versionJS";

		return execute(versionSnippet).orElse("Error");
	}

	@Override
	protected String language() {
		return Language.js.name();
	}
}
