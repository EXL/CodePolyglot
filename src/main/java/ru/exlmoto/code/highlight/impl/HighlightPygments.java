package ru.exlmoto.code.highlight.impl;

import org.springframework.stereotype.Component;

import ru.exlmoto.code.highlight.Highlight;
import ru.exlmoto.code.polyglot.impl.PolyglotPython;

@Component
public class HighlightPygments extends Highlight {
	private final PolyglotPython polyglotPython;

	public HighlightPygments(PolyglotPython polyglotPython) {
		this.polyglotPython = polyglotPython;
	}

	@Override
	public String getLibraryVersion() {
		final String librarySnippet =
			"import site" + "\n" +
			"from pygments import highlight" + "\n" +
			"from pygments.lexers import get_lexer_by_name" + "\n" +
			"from pygments.formatters import HtmlFormatter" + "\n" +
			"from pygments import __version__ as PygmentsVersion" + "\n" +
			"\n" +
			"PygmentsVersion";

		return polyglotPython.execute(librarySnippet).orElse("Error");
	}
}
