package ru.exlmoto.code.helper;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class FilterHelper {
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
}
