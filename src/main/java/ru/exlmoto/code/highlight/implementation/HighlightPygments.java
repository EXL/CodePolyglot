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

package ru.exlmoto.code.highlight.implementation;

//import org.python.util.PythonInterpreter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import ru.exlmoto.code.highlight.Highlight;
import ru.exlmoto.code.highlight.enumeration.Language;

import java.util.Optional;

@Component
public class HighlightPygments extends Highlight {
	private final Logger log = LoggerFactory.getLogger(HighlightPygments.class);

	private boolean useJython = false;
//	private final PythonInterpreter jython = new PythonInterpreter();

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
			"highlight(source, lexer, formatter)";

		return execute(renderLanguageSnippet);
	}

	@Override
	protected Optional<String> renderHtmlFromCodeAuto(String code) {
		importValue("source", code);

		final String renderAutoSnippet =
//			"source = \"\"\"\n" + StringUtils.escapeJava(code) + "\n\"\"\"" + "\n" +
			"formatter = HtmlFormatter(nowrap=True)" + "\n" +
			"lexer = guess_lexer(source)" + "\n" +
			"\n" +
			"highlight(source, lexer, formatter)";

		return execute(renderAutoSnippet);
	}

	@Override
	protected void importValue(String name, String value) {
//		if (useJython)
//			try {
//				jython.set(name, value);
//			} catch (RuntimeException re) {
//				log.error(String.format("Cannot import '%s' value to Jython context: '%s'.",
//					name, re.getLocalizedMessage()), re);
//			}
//		else
			super.importValue(name, value);
	}

	@Override
	protected Optional<String> execute(String sourceCode) {
//		if (useJython) {
//			try {
//				jython.exec(injectChunkToLastLineStart(sourceCode, "result = "));
//				return Optional.ofNullable(jython.get("result", String.class));
//			} catch (RuntimeException re) {
//				log.error(String.format("Cannot execute code with Jython: '%s'.", re.getLocalizedMessage()), re);
//				return Optional.empty();
//			}
//		} else
			return super.execute(sourceCode);
	}

	public void setUseJython(boolean useJython) {
		this.useJython = useJython;
	}

	protected String injectChunkToLastLineStart(String lines, String inject) {
		if (StringUtils.hasText(inject)) {
			final int index = lines.lastIndexOf("\n");
			return (index > 0) ?
				lines.substring(0, index) + "\n" + inject + lines.substring(index + 1) :
				inject + lines;
		}
		return lines;
	}
}
