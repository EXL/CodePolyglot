package ru.exlmoto.code.highlight.implementation;

import org.python.util.PythonInterpreter;

import org.springframework.stereotype.Component;

import ru.exlmoto.code.helper.UtilityHelper;
import ru.exlmoto.code.highlight.Highlight;
import ru.exlmoto.code.highlight.enumeration.Language;

import java.util.Optional;

@Component
public class HighlightPygments extends Highlight {
	private final UtilityHelper util;

	private boolean useJython = false;
	private PythonInterpreter jython = new PythonInterpreter();

	public HighlightPygments(UtilityHelper util) {
		this.util = util;
	}

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
			"from pygments.lexers import get_lexer_by_name, guess_lexer" + "\n" +
			"from pygments.formatters import HtmlFormatter" + "\n" +
			"from pygments import __version__ as PygmentsVersion" + "\n" +
			"\n" +
			"PygmentsVersion";

		return execute(librarySnippet).orElse("Error");
	}

	@Override
	public Optional<String> generateCssStyle(String theme) {
		final String styleSnippet =
			"HtmlFormatter(style='" + theme + "').get_style_defs('.highlight')";

		return execute(styleSnippet);
	}

	@Override
	protected Optional<String> renderHtmlFromCodeLanguage(String language, String code) {
		importValue("source", code);

		final String renderLanguageSnippet =
//			"source = \"\"\"\n" + StringUtils.escapeJava(code) + "\n\"\"\"" + "\n" +
			"formatter = HtmlFormatter(nowrap=True)" + "\n" +
			"lexer = get_lexer_by_name('" + language + "')" + "\n" +
			"\n" +
			"highlight(str(source), lexer, formatter)";

		return execute(renderLanguageSnippet);
	}

	@Override
	protected Optional<String> renderHtmlFromCodeAuto(String code) {
		importValue("source", code);

		final String renderAutoSnippet =
//			"source = \"\"\"\n" + StringUtils.escapeJava(code) + "\n\"\"\"" + "\n" +
			"formatter = HtmlFormatter(nowrap=True)" + "\n" +
			"lexer = guess_lexer(str(source))" + "\n" +
			"\n" +
			"highlight(str(source), lexer, formatter)";

		return execute(renderAutoSnippet);
	}

	@Override
	protected void importValue(String name, String value) {
		if (useJython)
			jython.set(name, value);
		else
			super.importValue(name, value);
	}

	@Override
	protected Optional<String> execute(String sourceCode) {
		if (useJython) {
			jython.exec(util.injectChunkToLastLineStart(sourceCode, "result="));
			return Optional.ofNullable(jython.get("result", String.class));
		} else
			return super.execute(sourceCode);
	}

	public void setUseJython(boolean useJython) {
		this.useJython = useJython;
	}
}
