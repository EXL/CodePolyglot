package ru.exlmoto.code.highlight.impl;

import org.springframework.stereotype.Component;

import ru.exlmoto.code.highlight.Highlight;
import ru.exlmoto.code.polyglot.impl.PolyglotJavaScript;
import ru.exlmoto.code.util.ResourceHelper;

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
}
