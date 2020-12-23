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

import org.springframework.stereotype.Component;

import ru.exlmoto.code.highlight.Highlight;
import ru.exlmoto.code.highlight.enumeration.Language;

import java.util.Optional;

//@Component
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
	public Optional<String> generateCssStyle(String theme) {
		final String styleSnippet =
			"Rouge::Theme.find('" + theme + "').render(scope: '.highlight')";

		return execute(styleSnippet);
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
