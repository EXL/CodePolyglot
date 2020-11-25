package ru.exlmoto.code.polyglot.impl;

import org.springframework.stereotype.Component;

import ru.exlmoto.code.polyglot.Language;
import ru.exlmoto.code.polyglot.Polyglot;

@Component
public class JavaScriptPolyglot extends Polyglot {
	@Override
	protected String executeAux(String sourceCode) {
		return polyglot.eval(Language.js.name(), sourceCode).asString();
	}

	@Override
	public String loadRequiredLibrariesAndGetVersion() {
		final String versionSnippet =
			"process.version";

		return execute(versionSnippet).orElse("VERSION ERROR!");
	}
}
