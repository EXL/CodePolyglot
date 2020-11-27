package ru.exlmoto.code.highlight.implementation;

import org.springframework.stereotype.Component;

import ru.exlmoto.code.highlight.Highlight;
import ru.exlmoto.code.highlight.enumeration.Language;

import java.util.Optional;

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
	protected Optional<String> renderHtmlFromCodeLanguage(String language, String code) {
		importValue("$source", code);

		final String renderLanguageSnippet =
//			"$source = %{" + StringUtils.escapeJava(code) + "}" + "\n" +
			"formatter = Rouge::Formatters::HTML.new" + "\n" +
			"lexer = Rouge::Lexer::find('" + language + "')" + "\n" +
			"\n" +
			"formatter.format(lexer.lex($source.to_str))";

		return execute(renderLanguageSnippet);
	}

	@Override
	protected Optional<String> renderHtmlFromCodeAuto(String code) {
		importValue("$source", code);

		final String renderAutoSnippet =
//			"$source = %{" + StringUtils.escapeJava(code) + "}" + "\n" +
			"formatter = Rouge::Formatters::HTML.new" + "\n" +
			"lexer = Rouge::Lexer::guess(source: $source.to_str)" + "\n" +
			"\n" +
			"formatter.format(lexer.lex($source.to_str))";

		return execute(renderAutoSnippet);
	}
}
