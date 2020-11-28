package ru.exlmoto.code.highlight.parser;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import ru.exlmoto.code.helper.UtilityHelper;

import java.util.Optional;

@Component
public class OptionsParser {
	private final UtilityHelper util;

	public OptionsParser(UtilityHelper util) {
		this.util = util;
	}

	public Options parseOptions(String options) {
		if (!StringUtils.hasText(options) || options.length() > Options.MAX_OPTION_STRING_LENGTH)
			return defaultOptions();

		final Options settings = new Options();
		String[] optionsArray = options.split("\\|");
		settings.setLanguage(scanOptionsForLanguage(optionsArray[0]));
		settings.setTable(scanOptionsForTable(optionsArray));
		scanOptionsForHighlightString(optionsArray, settings);
		return settings;
	}

	private Options defaultOptions() {
		return new Options();
	}

	private String scanOptionsForLanguage(String firstToken) {
		if (firstToken.equals(Options.DISABLE_TABLE_OPTION) || firstToken.contains(":"))
			return Options.DISABLE_HIGHLIGHT_OPTION;
		return util.getLong(firstToken).map((value) -> "").orElse(firstToken);
	}

	private boolean scanOptionsForTable(String[] options) {
		for (String option : options)
			if (option.equals(Options.DISABLE_TABLE_OPTION))
				return false;
		return true;
	}

	private void scanOptionsForHighlightString(String[] options, Options settings) {
		for (String option : options) {
			if (option.contains(":")) {
				String[] tokens = option.split(":");
				if (tokens.length == 2) {
					Optional<Long> first = util.getLong(tokens[0]);
					Optional<Long> second = util.getLong(tokens[1]);
					if (first.isPresent() && second.isPresent())
						if (first.get() > 0L && second.get() > 0L && first.get() <= second.get()) {
							settings.sethStart(first.get());
							settings.sethEnd(second.get());
							return;
						}
				}
			} else {
				Optional<Long> first = util.getLong(option);
				if (first.isPresent()) {
					settings.sethStart(first.get());
					settings.sethEnd(first.get());
					return;
				}
			}
		}
	}
}
