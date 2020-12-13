package ru.exlmoto.code.highlight.filter;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.exlmoto.code.helper.ResourceHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class TagCompensatorTest {
	@Autowired
	private TagCompensator compensator;

	@Autowired
	private ResourceHelper resource;

	@Test
	public void testCompensateTags() {
		String snippetFirstIn = resource.readFileToString("classpath:/expected/SnippetFirstIn.html.txt");
		String snippetFirstOut = resource.readFileToString("classpath:/expected/SnippetFirstOut.html.txt");
		String snippetSecondIn = resource.readFileToString("classpath:/expected/SnippetSecondIn.html.txt");
		String snippetSecondOut = resource.readFileToString("classpath:/expected/SnippetSecondOut.html.txt");

		compensator.compensateTags(snippetFirstIn).ifPresent(renderedHtml ->
			assertEquals(snippetFirstOut, renderedHtml));
		compensator.compensateTags(snippetSecondIn).ifPresent(renderedHtml ->
			assertEquals(snippetSecondOut, renderedHtml));
	}

	@Test
	public void testCompensateTagsErrors() {
		assertFalse(compensator.compensateTags(null).isPresent());
		assertFalse(compensator.compensateTags("").isPresent());
		assertFalse(compensator.compensateTags(" ").isPresent());
	}
}
