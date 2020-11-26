package ru.exlmoto.code.highlight.impl;

import org.springframework.stereotype.Component;

import org.thymeleaf.util.StringUtils;

import ru.exlmoto.code.highlight.Highlight;
import ru.exlmoto.code.highlight.Options;
import ru.exlmoto.code.polyglot.impl.PolyglotJavaScript;
import ru.exlmoto.code.util.ResourceHelper;

import java.util.Map;

@Component
public class HighlightJs extends Highlight {
	private final PolyglotJavaScript polyglotJavaScript;
	private final ResourceHelper resourceHelper;

	public HighlightJs(PolyglotJavaScript polyglotJavaScript, ResourceHelper resourceHelper) {
		this.polyglotJavaScript = polyglotJavaScript;
		this.resourceHelper = resourceHelper;
	}

	@Override
	public String getLibraryVersion() {
		String librarySnippet = null;
		librarySnippet += resourceHelper.readFileToString("classpath:highlight/highlight.pack.js");
		librarySnippet += "\n";
		librarySnippet += "hljs.versionString";

		return polyglotJavaScript.execute(librarySnippet).orElse("Error");
	}

	@Override
	public String getLanguageVersion() {
		return polyglotJavaScript.getLanguageVersion();
	}

	public String renderHtmlFromCode(Map<Options, String> options, String code) {
		polyglotJavaScript.importValue("source", code);

		final String renderSnippet =
//			"source = `\n" + StringUtils.escapeJava(code) + "\n`" + "\n" +
			"\n" +
			"hljs.highlight('" + options.get(Options.lang) + "', String(source)).value";

		return polyglotJavaScript.execute(renderSnippet).orElse("Error");
	}
}
