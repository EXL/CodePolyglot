package ru.exlmoto.code.highlight.filter;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class TagCompensatorTest {
	@Autowired
	private TagCompensator compensator;

	@Test
	public void testCompensateTags() {
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

	private final String snippetFirstIn =
		"<span class=\"hljs-string\">&quot;astring&quot;</span>\n" +
		"expression\n" +
		"<span class=\"hljs-string\">&quot;a <span class=\"hljs-subst\">${  <span class=\"hljs-string\">&quot;string&quot;</span> }</span> <span class=\"hljs-subst\">${<span class=\"hljs-string\">&#x27;c&#x27;</span>}</span> b&quot;</span>\n" +
		"<span class=\"hljs-string\">&quot;a <span class=\"hljs-subst\">${  <span class=\"hljs-string\">&quot;string <span class=\"hljs-variable\">$var</span> <span class=\"hljs-subst\">${subs}</span>&quot;</span> }</span> b&quot;</span>\n" +
		"<span class=\"hljs-string\">&quot;&quot;&quot;  <span class=\"hljs-subst\">${\n" +
		"    ${subst}\n" +
		"    <span class=\"hljs-string\">${subst}</span>\n" +
		"    ${s<span class=\"hljs-string\">ub</span>st}</span>\n" +
		"} &quot;&quot;&quot;</span>\n" +
		"<span class=\"hljs-string\">&quot;&quot;&quot;f=&quot;true&quot;&quot;&quot;&quot;</span>"
		;

	private final String snippetFirstOut =
		"<span class=\"hljs-string\">&quot;astring&quot;</span>\n" +
		"expression\n" +
		"<span class=\"hljs-string\">&quot;a <span class=\"hljs-subst\">${  <span class=\"hljs-string\">&quot;string&quot;</span> }</span> <span class=\"hljs-subst\">${<span class=\"hljs-string\">&#x27;c&#x27;</span>}</span> b&quot;</span>\n" +
		"<span class=\"hljs-string\">&quot;a <span class=\"hljs-subst\">${  <span class=\"hljs-string\">&quot;string <span class=\"hljs-variable\">$var</span> <span class=\"hljs-subst\">${subs}</span>&quot;</span> }</span> b&quot;</span>\n" +
		"<span class=\"hljs-string\">&quot;&quot;&quot;  <span class=\"hljs-subst\">${</span></span>\n" +
		"<span class=\"hljs-string\"><span class=\"hljs-subst\">    ${subst}</span></span>\n" +
		"<span class=\"hljs-string\"><span class=\"hljs-subst\">    <span class=\"hljs-string\">${subst}</span></span></span>\n" +
		"<span class=\"hljs-string\"><span class=\"hljs-subst\">    ${s<span class=\"hljs-string\">ub</span>st}</span></span>\n" +
		"<span class=\"hljs-string\">} &quot;&quot;&quot;</span>\n" +
		"<span class=\"hljs-string\">&quot;&quot;&quot;f=&quot;true&quot;&quot;&quot;&quot;</span>\n"
		;

	private final String snippetSecondIn =
		"\t<span class=\"hljs-meta\">@PostMapping(path = &quot;/api&quot;, produces = &quot;text/plain;charset=UTF-8&quot;)</span>\n" +
		"\t<span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> String <span class=\"hljs-title\">highlight</span><span class=\"hljs-params\">(InputStream inputDataStream,\n" +
		"\t                        <span class=\"hljs-meta\">@RequestParam(name = &quot;o&quot;, required = false, defaultValue = &quot;&quot;)</span> String options,\n" +
		"\t                        <span class=\"hljs-meta\">@RequestParam(name = &quot;h&quot;, required = false, defaultValue = &quot;HighlightJs&quot;)</span> String mode)</span> </span>{\n" +
		"\t\tScanner scanner = <span class=\"hljs-keyword\">new</span> Scanner(inputDataStream).useDelimiter(<span class=\"hljs-string\">&quot;\\\\A&quot;</span>); <span class=\"hljs-comment\">// https://stackoverflow.com/a/5445161</span>\n" +
		"\t\t<span class=\"hljs-keyword\">if</span> (scanner.hasNext()) {\n" +
		"\t\t\tString code = scanner.next();\n" +
		"\t\t\t<span class=\"hljs-keyword\">if</span> (StringUtils.hasText(code) &amp;&amp; code.length() &lt; config.getSnippetMaxLength()) {\n" +
		"\t\t\t\t<span class=\"hljs-keyword\">return</span> highlight.highlightCode(Mode.getMode(mode), util.getCorrectOptions(options), code);\n" +
		"\t\t\t}\n" +
		"\t\t}\n" +
		"\t\tscanner.close();\n" +
		"\t\t<span class=\"hljs-keyword\">return</span> <span class=\"hljs-string\">&quot;Error: Cannot highlight code snippet.&quot;</span>;\n" +
		"\t}"
		;

	private final String snippetSecondOut =
		"\t<span class=\"hljs-meta\">@PostMapping(path = &quot;/api&quot;, produces = &quot;text/plain;charset=UTF-8&quot;)</span>\n" +
		"\t<span class=\"hljs-function\"><span class=\"hljs-keyword\">public</span> String <span class=\"hljs-title\">highlight</span><span class=\"hljs-params\">(InputStream inputDataStream,</span></span>\n" +
		"<span class=\"hljs-function\"><span class=\"hljs-params\">\t                        <span class=\"hljs-meta\">@RequestParam(name = &quot;o&quot;, required = false, defaultValue = &quot;&quot;)</span> String options,</span></span>\n" +
		"<span class=\"hljs-function\"><span class=\"hljs-params\">\t                        <span class=\"hljs-meta\">@RequestParam(name = &quot;h&quot;, required = false, defaultValue = &quot;HighlightJs&quot;)</span> String mode)</span> </span>{\n" +
		"\t\tScanner scanner = <span class=\"hljs-keyword\">new</span> Scanner(inputDataStream).useDelimiter(<span class=\"hljs-string\">&quot;\\\\A&quot;</span>); <span class=\"hljs-comment\">// https://stackoverflow.com/a/5445161</span>\n" +
		"\t\t<span class=\"hljs-keyword\">if</span> (scanner.hasNext()) {\n" +
		"\t\t\tString code = scanner.next();\n" +
		"\t\t\t<span class=\"hljs-keyword\">if</span> (StringUtils.hasText(code) &amp;&amp; code.length() &lt; config.getSnippetMaxLength()) {\n" +
		"\t\t\t\t<span class=\"hljs-keyword\">return</span> highlight.highlightCode(Mode.getMode(mode), util.getCorrectOptions(options), code);\n" +
		"\t\t\t}\n" +
		"\t\t}\n" +
		"\t\tscanner.close();\n" +
		"\t\t<span class=\"hljs-keyword\">return</span> <span class=\"hljs-string\">&quot;Error: Cannot highlight code snippet.&quot;</span>;\n" +
		"\t}\n"
		;
}
