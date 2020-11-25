package ru.exlmoto.code.polyglot.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import ru.exlmoto.code.polyglot.Language;
import ru.exlmoto.code.polyglot.Polyglot;
import ru.exlmoto.code.util.ResourceHelper;

@Component
public class JavaScriptPolyglot extends Polyglot {
	private final Logger log = LoggerFactory.getLogger(JavaScriptPolyglot.class);

	private final ResourceHelper resourceHelper;

	public JavaScriptPolyglot(ResourceHelper resourceHelper) {
		this.resourceHelper = resourceHelper;
	}

	@Override
	protected String executeAux(String sourceCode) {
		return polyglot.eval(Language.js.name(), sourceCode).asString();
	}

	@Override
	public String loadRequiredLibrariesAndGetVersion() {
		String versionSnippet = resourceHelper.readFileToString("classpath:highlight/highlight.pack.js");
		versionSnippet += "\n";
		versionSnippet += "Graal.versionJS";

		// System.out.println(polyglot.getEngine().getLanguages().get(Language.js.name()).getVersion());

		return execute(versionSnippet).orElse("VERSION ERROR!");
	}
}
