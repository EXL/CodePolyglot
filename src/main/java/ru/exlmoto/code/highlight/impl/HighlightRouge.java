package ru.exlmoto.code.highlight.impl;

import org.springframework.stereotype.Component;

import ru.exlmoto.code.highlight.Highlight;
import ru.exlmoto.code.highlight.Options;
import ru.exlmoto.code.polyglot.impl.PolyglotRuby;

import java.util.Map;

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

	public String renderHtmlFromCode(Map<Options, String> options, String code) {
		polyglotRuby.importValue("$code", code);

		final String renderSnippet =
//			"source = %{" + code + "}" + "\n" +
			"formatter = Rouge::Formatters::HTML.new" + "\n" +
			"formatterTable = Rouge::Formatters::HTMLLineTable.new(formatter, opts={})" + "\n" +
			"lexer = Rouge::Lexer::find('" + options.get(Options.lang) + "')" + "\n" +
			"\n" +
//			"print('1' + code)" + "\n" +
//			"print('2' + $code)" + "\n" +
			"formatterTable.format(lexer.lex($code.to_s))";

		return polyglotRuby.execute(renderSnippet).orElse("Error");
	}
}
