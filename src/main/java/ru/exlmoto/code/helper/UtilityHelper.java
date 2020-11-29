package ru.exlmoto.code.helper;

import org.springframework.data.domain.Page;
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

	public String getDateFromTimeStamp(String dateFormat, Locale dateLocale, long timestamp) {
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
			options.replaceAll(";", "|").replaceAll(",", ":").replaceAll(" ", "") : options;
	}

	public String injectChunkToLastLineStart(String lines, String inject) {
		final int index = lines.lastIndexOf("\n");
		return (index > 0) ? lines.substring(0, index) + "\n" + inject + lines.substring(index + 1) : inject + lines;
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
			return getDateFromTimeStamp(config.getDateFormat(), Locale.forLanguageTag(language), timestamp);
	}
}