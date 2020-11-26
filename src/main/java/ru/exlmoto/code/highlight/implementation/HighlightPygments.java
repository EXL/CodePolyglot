package ru.exlmoto.code.highlight.implementation;

import org.springframework.stereotype.Component;

import ru.exlmoto.code.highlight.Highlight;
import ru.exlmoto.code.highlight.enumeration.Language;
import ru.exlmoto.code.highlight.enumeration.Options;

import java.util.Map;

@Component
public class HighlightPygments extends Highlight {
	@Override
	protected String language() {
		return Language.python.name();
	}

	@Override
	public String getLanguageVersion() {
		final String versionSnippet =
			"import platform" + "\n" +
			"\n" +
			"platform.python_version()";

		return execute(versionSnippet).orElse("Error");
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

		return execute(librarySnippet).orElse("Error");
	}

	@Override
	public String renderHtmlFromCode(Map<Options, String> options, String code) {
		importValue("source", code);

		final String renderSnippet =
//			"source = \"\"\"\n" + StringUtils.escapeJava(code) + "\n\"\"\"" + "\n" +
//			"formatter = HtmlFormatter(linenos=True)" + "\n" +
			"formatter = HtmlFormatter(nowrap=True)" + "\n" +
			"lexer = get_lexer_by_name('" + options.get(Options.lang) + "')" + "\n" +
			"\n" +
			"highlight(str(source), lexer, formatter)";

		return execute(renderSnippet).orElse("Error");
	}
}
