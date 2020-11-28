package ru.exlmoto.code.highlight;

import org.graalvm.polyglot.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.StringUtils;

import ru.exlmoto.code.highlight.enumeration.Language;
import ru.exlmoto.code.highlight.parser.Options;

import java.util.Optional;

public abstract class Highlight {
	private final Logger log = LoggerFactory.getLogger(Highlight.class);

	protected final Context polyglot = Context.newBuilder(Language.names()).allowAllAccess(true).allowIO(true).build();

	protected Optional<String> execute(String sourceCode) {
		try {
			String output = polyglot.eval(language(), sourceCode).asString();
			if (StringUtils.hasText(output)) {
				return Optional.of(output);
			}
		} catch (RuntimeException re) {
			log.error(String.format("Cannot execute '%s' code: '%s'.", language(), re.getLocalizedMessage()), re);
			// return Optional.of("Error: " + re.getLocalizedMessage());
		}
		return Optional.empty();
	}

	protected void importValue(String name, String value) {
		try {
			polyglot.getBindings(language()).putMember(name, value);
		} catch (RuntimeException re) {
			log.error(String.format("Cannot import '%s' value to '%s' language context: '%s'.",
				name, language(), re.getLocalizedMessage()), re);
		}
	}

	protected abstract String language();

	protected abstract Optional<String> renderHtmlFromCodeLanguage(String language, String code);

	protected abstract Optional<String> renderHtmlFromCodeAuto(String code);

	public abstract String getLanguageVersion();

	public abstract String getLibraryVersion();

	public Optional<String> renderHtmlFromCode(String language, String code) {
		return (language.equals(Options.GUESS_LEXER_OPTION)) ?
			renderHtmlFromCodeAuto(code) : renderHtmlFromCodeLanguage(language, code);
	}

	// TODO: CSS string?
}
