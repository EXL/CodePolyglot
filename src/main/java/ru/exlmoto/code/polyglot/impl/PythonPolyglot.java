package ru.exlmoto.code.polyglot.impl;

import org.springframework.stereotype.Component;

import ru.exlmoto.code.polyglot.Language;
import ru.exlmoto.code.polyglot.Polyglot;

@Component
public class PythonPolyglot extends Polyglot {
	@Override
	protected String executeAux(String sourceCode) {
		return polyglot.eval(Language.python.name(), sourceCode).asString();
	}

	@Override
	public String loadRequiredLibrariesAndGetVersion() {
		final String versionSnippet =
			"import platform" + "\n" +
			"import site" + "\n" +
			"from pygments import highlight" + "\n" +
			"from pygments.lexers import get_lexer_by_name" + "\n" +
			"from pygments.formatters import HtmlFormatter" + "\n" +
			"\n" +
			"platform.python_version()";

		return execute(versionSnippet).orElse("VERSION ERROR!");
	}
}
