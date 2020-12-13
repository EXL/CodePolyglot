package ru.exlmoto.code.highlight.parser;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OptionsParserTest {
	@Autowired
	private OptionsParser parser;

	@Test
	public void testParseOptions() {
		Options options = parser.parseOptions("java");
		assertTrue(checkOptions(options, "java", true, 0L, 0L));

		options = parser.parseOptions("auto");
		assertTrue(checkOptions(options, Options.GUESS_LEXER_OPTION, true, 0L, 0L));

		options = parser.parseOptions("java|nolines");
		assertTrue(checkOptions(options, "java", false, 0L, 0L));

		options = parser.parseOptions("java|nolines|30");
		assertTrue(checkOptions(options, "java", false, 30L, 30L));

		options = parser.parseOptions("java|nolines|15:30");
		assertTrue(checkOptions(options, "java", false, 15L, 30L));

		options = parser.parseOptions("30");
		assertTrue(checkOptions(options, Options.DISABLE_HIGHLIGHT_OPTION, true, 30L, 30L));

		options = parser.parseOptions("15:30");
		assertTrue(checkOptions(options, Options.DISABLE_HIGHLIGHT_OPTION, true, 15L, 30L));

		options = parser.parseOptions("nolines|15:30");
		assertTrue(checkOptions(options, Options.DISABLE_HIGHLIGHT_OPTION, false, 15L, 30L));

		options = parser.parseOptions("java|15:30");
		assertTrue(checkOptions(options, "java", true, 15L, 30L));
	}

	@Test
	public void testParseOptionsDefaults() {
		Options options = parser.parseOptions(null);
		assertTrue(checkOptions(options, Options.DISABLE_HIGHLIGHT_OPTION, true, 0L, 0L));

		options = parser.parseOptions("");
		assertTrue(checkOptions(options, Options.DISABLE_HIGHLIGHT_OPTION, true, 0L, 0L));

		options = parser.parseOptions(" ");
		assertTrue(checkOptions(options, Options.DISABLE_HIGHLIGHT_OPTION, true, 0L, 0L));

		options = parser.parseOptions("to long string to long string to long string to long string to long string");
		assertTrue(checkOptions(options, Options.DISABLE_HIGHLIGHT_OPTION, true, 0L, 0L));
	}

	@Test
	public void testParseOptionsErrors() {
		Options options = parser.parseOptions("java|nolines|30:15");
		assertTrue(checkOptions(options, "java", false, 0L, 0L));

		options = parser.parseOptions("java|nolines|-10:-13");
		assertTrue(checkOptions(options, "java", false, 0L, 0L));

		options = parser.parseOptions("java|nolines|-13:-10");
		assertTrue(checkOptions(options, "java", false, 0L, 0L));

		options = parser.parseOptions("java|nolines|");
		assertTrue(checkOptions(options, "java", false, 0L, 0L));

		options = parser.parseOptions("java|");
		assertTrue(checkOptions(options, "java", true, 0L, 0L));

		options = parser.parseOptions("|");
		assertTrue(checkOptions(options, Options.DISABLE_HIGHLIGHT_OPTION, true, 0L, 0L));
	}

	private boolean checkOptions(Options options, String lang, boolean table, long start, long end) {
		return
			options.getLanguage().equals(lang) &&
			(options.isTable() == table) &&
			(options.gethStart() == start) &&
			(options.gethEnd() == end);
	}
}
