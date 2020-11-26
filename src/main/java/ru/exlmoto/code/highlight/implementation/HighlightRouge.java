package ru.exlmoto.code.highlight.implementation;

import org.springframework.stereotype.Component;

import ru.exlmoto.code.highlight.Highlight;
import ru.exlmoto.code.highlight.enumeration.Language;
import ru.exlmoto.code.highlight.enumeration.Options;

import java.util.Map;

@Component
public class HighlightRouge extends Highlight {
	@Override
	protected String language() {
		return Language.ruby.name();
	}

	@Override
	public String getLanguageVersion() {
		final String versionSnippet =
			"RUBY_VERSION";

		return execute(versionSnippet).orElse("Error");
	}

	@Override
	public String getLibraryVersion() {
		final String librarySnippet =
			"require 'rouge'" + "\n" +
			"\n" +
			"Rouge::version()";

		return execute(librarySnippet).orElse("Error");
	}

	@Override
	public String renderHtmlFromCode(Map<Options, String> options, String code) {
		importValue("$source", code);

		final String renderSnippet =
//			"$source = %{" + StringUtils.escapeJava(code) + "}" + "\n" +
			"formatter = Rouge::Formatters::HTML.new" + "\n" +
//			"formatterTable = Rouge::Formatters::HTMLLineTable.new(formatter, opts={})" + "\n" +
			"lexer = Rouge::Lexer::find('" + options.get(Options.lang) + "')" + "\n" +
			"\n" +
			"formatter.format(lexer.lex($source.to_str))";

		return execute(renderSnippet).orElse("Error");
	}
}
