/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 EXL <exlmotodev@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

	public abstract Optional<String> generateCssStyle(String theme);
}
