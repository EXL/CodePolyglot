package ru.exlmoto.code.highlight.impl;

import org.springframework.stereotype.Component;

import org.thymeleaf.util.StringUtils;

import ru.exlmoto.code.highlight.Highlight;
import ru.exlmoto.code.highlight.Options;
import ru.exlmoto.code.polyglot.impl.PolyglotPython;

import java.util.Map;

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

	@Override
	public String getLanguageVersion() {
		return polyglotPython.getLanguageVersion();
	}

	public String renderHtmlFromCode(Map<Options, String> options, String code) {
		polyglotPython.importValue("source", code);

		final String renderSnippet =
//			"source = \"\"\"\n" + StringUtils.escapeJava(code) + "\n\"\"\"" + "\n" +
			"formatter = HtmlFormatter(linenos=True)" + "\n" +
			"lexer = get_lexer_by_name('" + options.get(Options.lang) + "')" + "\n" +
			"\n" +
			"highlight(str(source), lexer, formatter)";

		return polyglotPython.execute(renderSnippet).orElse("Error");
	}
}
