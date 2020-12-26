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

package ru.exlmoto.code.helper;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import ru.exlmoto.code.configuration.CodeConfiguration;
import ru.exlmoto.code.entity.CodeEntity;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
public class UtilityHelper {
	private final CodeConfiguration config;

	public UtilityHelper(CodeConfiguration config) {
		this.config = config;
	}

	public long getCurrentUnixTime() {
		return System.currentTimeMillis() / 1000L;
	}

	public String getDateFromTimestamp(String dateFormat, Locale dateLocale, long timestamp) {
		return DateTimeFormatter.ofPattern(dateFormat)
			.withLocale(dateLocale).withZone(ZoneId.systemDefault()).format(Instant.ofEpochSecond(timestamp));
	}

	public Optional<Long> getLong(String number) {
		if (StringUtils.hasText(number))
			try {
				return Optional.of(Long.parseLong(number));
			} catch (NumberFormatException ignored) { }
		return Optional.empty();
	}

	public String getCorrectOptions(String options) {
		return StringUtils.hasText(options) ?
			options
				.replaceAll(";", "|")
				.replaceAll(",", ":")
				.replaceAll(" ", "")
				.replaceAll("\r", "")
				.replaceAll("\n", "") :
			options;
	}

	public List<Pair<String, String>> generateSnippetLinks(List<CodeEntity> snippets, String language) {
		List<Pair<String, String>> links = new ArrayList<>();
		snippets.forEach((snippet) ->
			links.add(Pair.of(getSimpleSnippetName(snippet.getTitle(), language, snippet.getTimestamp()),
				String.valueOf(snippet.getId()))));
		return links;
	}

	public String getSimpleSnippetName(String title, String language, long timestamp) {
		if (StringUtils.hasText(title))
			return (title.length() > 10) ? title.substring(0, 10) + "…" : title;
		else
			return getDateFromTimestamp(config.getDateFormat(), Locale.forLanguageTag(language), timestamp);
	}
}
