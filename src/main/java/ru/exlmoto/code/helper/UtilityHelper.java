package ru.exlmoto.code.helper;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class UtilityHelper {
	public long getCurrentUnixTime() {
		return System.currentTimeMillis() / 1000L;
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
}
