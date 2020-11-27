package ru.exlmoto.code.highlight.implementation;

import org.springframework.stereotype.Component;

import ru.exlmoto.code.highlight.Highlight;
import ru.exlmoto.code.highlight.enumeration.Language;

import java.util.Optional;

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
	public Optional<String> renderHtmlFromCodeLanguage(String language, String code) {
		importValue("source", code);

		final String renderSnippet =
//			"source = \"\"\"\n" + StringUtils.escapeJava(code) + "\n\"\"\"" + "\n" +
			"formatter = HtmlFormatter(nowrap=True)" + "\n" +
			"lexer = get_lexer_by_name('" + language + "')" + "\n" +
			"\n" +
			"highlight(str(source), lexer, formatter)";

		return execute(renderSnippet);
	}

	@Override
	public Optional<String> renderHtmlFromCodeAuto(String code) {
		return Optional.empty();
	}
}
