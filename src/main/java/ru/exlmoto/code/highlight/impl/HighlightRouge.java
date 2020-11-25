package ru.exlmoto.code.highlight.impl;

import org.springframework.stereotype.Component;

import ru.exlmoto.code.highlight.Highlight;
import ru.exlmoto.code.polyglot.impl.PolyglotRuby;

@Component
public class HighlightRouge extends Highlight {
	private final PolyglotRuby polyglotRuby;

	public HighlightRouge(PolyglotRuby polyglotRuby) {
		this.polyglotRuby = polyglotRuby;
	}

	@Override
	public String getLibraryVersion() {
		final String librarySnippet =
			"require 'rouge'" + "\n" +
			"\n" +
			"Rouge::version()";

		return polyglotRuby.execute(librarySnippet).orElse("Error");
	}
}
