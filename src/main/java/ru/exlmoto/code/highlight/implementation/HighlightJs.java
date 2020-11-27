package ru.exlmoto.code.highlight.implementation;

import org.graalvm.home.Version;

import org.springframework.stereotype.Component;

import ru.exlmoto.code.highlight.Highlight;
import ru.exlmoto.code.highlight.enumeration.Language;
import ru.exlmoto.code.helper.ResourceHelper;

import java.util.Optional;

@Component
public class HighlightJs extends Highlight {
	private final ResourceHelper resourceHelper;

	public HighlightJs(ResourceHelper resourceHelper) {
		this.resourceHelper = resourceHelper;
	}

	@Override
	protected String language() {
		return Language.js.name();
	}

	@Override
	public String getLanguageVersion() {
		/*
		 * Additional information:
		 *  https://github.com/graalvm/graaljs/issues/378
		 */
		final String versionSnippet;
		if (Version.getCurrent().compareTo(Version.parse("21.0.0")) >= 0)
			versionSnippet =
				"Graal.versionECMAScript";
		else
			versionSnippet =
				"Graal.versionJS";

		return execute(versionSnippet).orElse("Error");
	}

	@Override
	public String getLibraryVersion() {
		String librarySnippet = null;
		librarySnippet += resourceHelper.readFileToString("classpath:highlight/highlight.pack.js");
		librarySnippet += "\n";
		librarySnippet += "hljs.versionString";

		return execute(librarySnippet).orElse("Error");
	}

	@Override
	protected Optional<String> renderHtmlFromCodeLanguage(String language, String code) {
		importValue("source", code);

		final String renderLanguageSnippet =
//			"source = `\n" + StringUtils.escapeJava(code) + "\n`" + "\n" +
			"\n" +
			"hljs.highlight('" + language + "', String(source)).value";

		return execute(renderLanguageSnippet);
	}

	@Override
	protected Optional<String> renderHtmlFromCodeAuto(String code) {
		importValue("source", code);

		final String renderAutoSnippet =
//			"source = `\n" + StringUtils.escapeJava(code) + "\n`" + "\n" +
			"\n" +
			"hljs.highlightAuto(String(source)).value";

		return execute(renderAutoSnippet);
	}
}
