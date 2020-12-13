package ru.exlmoto.code.highlight.implementation;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HighlightPygmentsTest {
	@Autowired
	private HighlightPygments pygments;

	@Test
	public void testInjectChunkToLastLineStart() {
		final String renderLanguageSnippetIn =
//			"source = \"\"\"\n" + StringUtils.escapeJava(code) + "\n\"\"\"" + "\n" +
			"formatter = HtmlFormatter(nowrap=True)" + "\n" +
			"lexer = get_lexer_by_name('" + "java" + "')" + "\n" +
			"\n" +
			"highlight(source, lexer, formatter)";

		final String renderLanguageSnippetOut =
//			"source = \"\"\"\n" + StringUtils.escapeJava(code) + "\n\"\"\"" + "\n" +
			"formatter = HtmlFormatter(nowrap=True)" + "\n" +
			"lexer = get_lexer_by_name('" + "java" + "')" + "\n" +
			"\n" +
			"result = highlight(source, lexer, formatter)";

		assertEquals(renderLanguageSnippetIn,
			pygments.injectChunkToLastLineStart(renderLanguageSnippetIn, null));
		assertEquals(renderLanguageSnippetOut,
			pygments.injectChunkToLastLineStart(renderLanguageSnippetIn, "result = "));
	}
}
