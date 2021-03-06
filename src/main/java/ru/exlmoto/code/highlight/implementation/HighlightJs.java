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
		String librarySnippet = resourceHelper.readFileToString("classpath:highlight/highlight.pack.js");
		librarySnippet += "\n";
		librarySnippet += "hljs.versionString";

		return execute(librarySnippet).orElse("Error");
	}

	@Override
	public Optional<String> generateCssStyle(String theme) {
		return Optional.of("Error: CSS style generation isn't available in Highlight.js, sorry.");
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
