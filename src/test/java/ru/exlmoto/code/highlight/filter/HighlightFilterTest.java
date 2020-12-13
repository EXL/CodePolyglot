package ru.exlmoto.code.highlight.filter;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HighlightFilterTest {
	@Autowired
	private HighlightFilter filter;

	@Test
	public void testFilterCarriageReturn() {
		assertEquals("testtest", filter.filterCarriageReturn("test\rtest"));
		assertEquals("test\ntest", filter.filterCarriageReturn("test\n\rtest"));
		assertEquals("test\ntest", filter.filterCarriageReturn("test\r\ntest"));
		assertEquals("test\ntest\n", filter.filterCarriageReturn("test\r\ntest\r\n"));
		assertEquals("test\ntest\n", filter.filterCarriageReturn("test\r\ntest\n"));
		assertEquals("test\ntest", filter.filterCarriageReturn("test\r\ntest\r"));
	}

	@Test
	public void testTableCode() {
		assertEquals(tableCode, filter.tableCode(snippet, 0L, 0L));
		assertEquals(tableCodeHighlight, filter.tableCode(snippet, 3L, 5L));
	}

	@Test
	public void testTableCodePlain() {
		assertEquals(tableCodePlain, filter.tableCodePlain(snippet, 0L, 0L));
		assertEquals(tableCodePlainHighlight, filter.tableCodePlain(snippet, 3L, 5L));
	}

	@Test
	public void testSimpleCode() {
		assertEquals(simpleCode, filter.simpleCode(snippet, 0L, 0L));
		assertEquals(simpleCodeHighlight, filter.simpleCode(snippet, 3L, 5L));
	}

	@Test
	public void testPlainCode() {
		assertEquals(plainCode, filter.plainCode(snippet, 0L, 0L));
		assertEquals(plainCodeHighlight, filter.plainCode(snippet, 3L, 5L));
	}

	private final String snippet =
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

	private final String tableCode =
		"<table class=\"code-table\"><tr id=\"line-1\"><td class=\"table-line l-table\"><a href=\"#line-1\">1</a></td><td class=\"code-line l-code\"><span class=\"hljs-string\">&quot;astring&quot;</span>\n" +
		"</td></tr><tr id=\"line-2\"><td class=\"table-line d-table\"><a href=\"#line-2\">2</a></td><td class=\"code-line d-code\">expression\n" +
		"</td></tr><tr id=\"line-3\"><td class=\"table-line l-table\"><a href=\"#line-3\">3</a></td><td class=\"code-line l-code\"><span class=\"hljs-string\">&quot;a <span class=\"hljs-subst\">${  <span class=\"hljs-string\">&quot;string&quot;</span> }</span> <span class=\"hljs-subst\">${<span class=\"hljs-string\">&#x27;c&#x27;</span>}</span> b&quot;</span>\n" +
		"</td></tr><tr id=\"line-4\"><td class=\"table-line d-table\"><a href=\"#line-4\">4</a></td><td class=\"code-line d-code\"><span class=\"hljs-string\">&quot;a <span class=\"hljs-subst\">${  <span class=\"hljs-string\">&quot;string <span class=\"hljs-variable\">$var</span> <span class=\"hljs-subst\">${subs}</span>&quot;</span> }</span> b&quot;</span>\n" +
		"</td></tr><tr id=\"line-5\"><td class=\"table-line l-table\"><a href=\"#line-5\">5</a></td><td class=\"code-line l-code\"><span class=\"hljs-string\">&quot;&quot;&quot;  <span class=\"hljs-subst\">${</span></span>\n" +
		"</td></tr><tr id=\"line-6\"><td class=\"table-line d-table\"><a href=\"#line-6\">6</a></td><td class=\"code-line d-code\"><span class=\"hljs-string\"><span class=\"hljs-subst\">    ${subst}</span></span>\n" +
		"</td></tr><tr id=\"line-7\"><td class=\"table-line l-table\"><a href=\"#line-7\">7</a></td><td class=\"code-line l-code\"><span class=\"hljs-string\"><span class=\"hljs-subst\">    <span class=\"hljs-string\">${subst}</span></span></span>\n" +
		"</td></tr><tr id=\"line-8\"><td class=\"table-line d-table\"><a href=\"#line-8\">8</a></td><td class=\"code-line d-code\"><span class=\"hljs-string\"><span class=\"hljs-subst\">    ${s<span class=\"hljs-string\">ub</span>st}</span></span>\n" +
		"</td></tr><tr id=\"line-9\"><td class=\"table-line l-table\"><a href=\"#line-9\">9</a></td><td class=\"code-line l-code\"><span class=\"hljs-string\">} &quot;&quot;&quot;</span>\n" +
		"</td></tr><tr id=\"line-10\"><td class=\"table-line d-table\"><a href=\"#line-10\"><strong>10</strong></a></td><td class=\"code-line d-code\"><span class=\"hljs-string\">&quot;&quot;&quot;f=&quot;true&quot;&quot;&quot;&quot;</span>\n" +
		"</td></tr></table>"
		;

	private final String tableCodeHighlight =
		"<table class=\"code-table\"><tr id=\"line-1\"><td class=\"table-line l-table\"><a href=\"#line-1\">1</a></td><td class=\"code-line l-code\"><span class=\"hljs-string\">&quot;astring&quot;</span>\n" +
		"</td></tr><tr id=\"line-2\"><td class=\"table-line d-table\"><a href=\"#line-2\">2</a></td><td class=\"code-line d-code\">expression\n" +
		"</td></tr><tr id=\"line-3\" class=\"hll\"><td class=\"table-line\"><a href=\"#line-3\">3</a></td><td class=\"code-line\"><span class=\"hljs-string\">&quot;a <span class=\"hljs-subst\">${  <span class=\"hljs-string\">&quot;string&quot;</span> }</span> <span class=\"hljs-subst\">${<span class=\"hljs-string\">&#x27;c&#x27;</span>}</span> b&quot;</span>\n" +
		"</td></tr><tr id=\"line-4\" class=\"hll\"><td class=\"table-line\"><a href=\"#line-4\">4</a></td><td class=\"code-line\"><span class=\"hljs-string\">&quot;a <span class=\"hljs-subst\">${  <span class=\"hljs-string\">&quot;string <span class=\"hljs-variable\">$var</span> <span class=\"hljs-subst\">${subs}</span>&quot;</span> }</span> b&quot;</span>\n" +
		"</td></tr><tr id=\"line-5\" class=\"hll\"><td class=\"table-line\"><a href=\"#line-5\">5</a></td><td class=\"code-line\"><span class=\"hljs-string\">&quot;&quot;&quot;  <span class=\"hljs-subst\">${</span></span>\n" +
		"</td></tr><tr id=\"line-6\"><td class=\"table-line d-table\"><a href=\"#line-6\">6</a></td><td class=\"code-line d-code\"><span class=\"hljs-string\"><span class=\"hljs-subst\">    ${subst}</span></span>\n" +
		"</td></tr><tr id=\"line-7\"><td class=\"table-line l-table\"><a href=\"#line-7\">7</a></td><td class=\"code-line l-code\"><span class=\"hljs-string\"><span class=\"hljs-subst\">    <span class=\"hljs-string\">${subst}</span></span></span>\n" +
		"</td></tr><tr id=\"line-8\"><td class=\"table-line d-table\"><a href=\"#line-8\">8</a></td><td class=\"code-line d-code\"><span class=\"hljs-string\"><span class=\"hljs-subst\">    ${s<span class=\"hljs-string\">ub</span>st}</span></span>\n" +
		"</td></tr><tr id=\"line-9\"><td class=\"table-line l-table\"><a href=\"#line-9\">9</a></td><td class=\"code-line l-code\"><span class=\"hljs-string\">} &quot;&quot;&quot;</span>\n" +
		"</td></tr><tr id=\"line-10\"><td class=\"table-line d-table\"><a href=\"#line-10\"><strong>10</strong></a></td><td class=\"code-line d-code\"><span class=\"hljs-string\">&quot;&quot;&quot;f=&quot;true&quot;&quot;&quot;&quot;</span>\n" +
		"</td></tr></table>"
		;

	private final String tableCodePlain =
		"<table class=\"code-table\"><tr id=\"line-1\"><td class=\"table-line l-table\"><a href=\"#line-1\">1</a></td><td class=\"code-line l-code\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;astring&amp;quot;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-2\"><td class=\"table-line d-table\"><a href=\"#line-2\">2</a></td><td class=\"code-line d-code\">expression\n" +
		"</td></tr><tr id=\"line-3\"><td class=\"table-line l-table\"><a href=\"#line-3\">3</a></td><td class=\"code-line l-code\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;a &lt;span class=&#34;hljs-subst&#34;&gt;${  &lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;string&amp;quot;&lt;/span&gt; }&lt;/span&gt; &lt;span class=&#34;hljs-subst&#34;&gt;${&lt;span class=&#34;hljs-string&#34;&gt;&amp;#x27;c&amp;#x27;&lt;/span&gt;}&lt;/span&gt; b&amp;quot;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-4\"><td class=\"table-line d-table\"><a href=\"#line-4\">4</a></td><td class=\"code-line d-code\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;a &lt;span class=&#34;hljs-subst&#34;&gt;${  &lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;string &lt;span class=&#34;hljs-variable&#34;&gt;$var&lt;/span&gt; &lt;span class=&#34;hljs-subst&#34;&gt;${subs}&lt;/span&gt;&amp;quot;&lt;/span&gt; }&lt;/span&gt; b&amp;quot;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-5\"><td class=\"table-line l-table\"><a href=\"#line-5\">5</a></td><td class=\"code-line l-code\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;&amp;quot;&amp;quot;  &lt;span class=&#34;hljs-subst&#34;&gt;${&lt;/span&gt;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-6\"><td class=\"table-line d-table\"><a href=\"#line-6\">6</a></td><td class=\"code-line d-code\">&lt;span class=&#34;hljs-string&#34;&gt;&lt;span class=&#34;hljs-subst&#34;&gt;    ${subst}&lt;/span&gt;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-7\"><td class=\"table-line l-table\"><a href=\"#line-7\">7</a></td><td class=\"code-line l-code\">&lt;span class=&#34;hljs-string&#34;&gt;&lt;span class=&#34;hljs-subst&#34;&gt;    &lt;span class=&#34;hljs-string&#34;&gt;${subst}&lt;/span&gt;&lt;/span&gt;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-8\"><td class=\"table-line d-table\"><a href=\"#line-8\">8</a></td><td class=\"code-line d-code\">&lt;span class=&#34;hljs-string&#34;&gt;&lt;span class=&#34;hljs-subst&#34;&gt;    ${s&lt;span class=&#34;hljs-string&#34;&gt;ub&lt;/span&gt;st}&lt;/span&gt;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-9\"><td class=\"table-line l-table\"><a href=\"#line-9\">9</a></td><td class=\"code-line l-code\">&lt;span class=&#34;hljs-string&#34;&gt;} &amp;quot;&amp;quot;&amp;quot;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-10\"><td class=\"table-line d-table\"><a href=\"#line-10\"><strong>10</strong></a></td><td class=\"code-line d-code\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;&amp;quot;&amp;quot;f=&amp;quot;true&amp;quot;&amp;quot;&amp;quot;&amp;quot;&lt;/span&gt;\n" +
		"</td></tr></table>"
		;

	private final String tableCodePlainHighlight =
		"<table class=\"code-table\"><tr id=\"line-1\"><td class=\"table-line l-table\"><a href=\"#line-1\">1</a></td><td class=\"code-line l-code\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;astring&amp;quot;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-2\"><td class=\"table-line d-table\"><a href=\"#line-2\">2</a></td><td class=\"code-line d-code\">expression\n" +
		"</td></tr><tr id=\"line-3\" class=\"hll\"><td class=\"table-line\"><a href=\"#line-3\">3</a></td><td class=\"code-line\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;a &lt;span class=&#34;hljs-subst&#34;&gt;${  &lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;string&amp;quot;&lt;/span&gt; }&lt;/span&gt; &lt;span class=&#34;hljs-subst&#34;&gt;${&lt;span class=&#34;hljs-string&#34;&gt;&amp;#x27;c&amp;#x27;&lt;/span&gt;}&lt;/span&gt; b&amp;quot;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-4\" class=\"hll\"><td class=\"table-line\"><a href=\"#line-4\">4</a></td><td class=\"code-line\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;a &lt;span class=&#34;hljs-subst&#34;&gt;${  &lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;string &lt;span class=&#34;hljs-variable&#34;&gt;$var&lt;/span&gt; &lt;span class=&#34;hljs-subst&#34;&gt;${subs}&lt;/span&gt;&amp;quot;&lt;/span&gt; }&lt;/span&gt; b&amp;quot;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-5\" class=\"hll\"><td class=\"table-line\"><a href=\"#line-5\">5</a></td><td class=\"code-line\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;&amp;quot;&amp;quot;  &lt;span class=&#34;hljs-subst&#34;&gt;${&lt;/span&gt;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-6\"><td class=\"table-line d-table\"><a href=\"#line-6\">6</a></td><td class=\"code-line d-code\">&lt;span class=&#34;hljs-string&#34;&gt;&lt;span class=&#34;hljs-subst&#34;&gt;    ${subst}&lt;/span&gt;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-7\"><td class=\"table-line l-table\"><a href=\"#line-7\">7</a></td><td class=\"code-line l-code\">&lt;span class=&#34;hljs-string&#34;&gt;&lt;span class=&#34;hljs-subst&#34;&gt;    &lt;span class=&#34;hljs-string&#34;&gt;${subst}&lt;/span&gt;&lt;/span&gt;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-8\"><td class=\"table-line d-table\"><a href=\"#line-8\">8</a></td><td class=\"code-line d-code\">&lt;span class=&#34;hljs-string&#34;&gt;&lt;span class=&#34;hljs-subst&#34;&gt;    ${s&lt;span class=&#34;hljs-string&#34;&gt;ub&lt;/span&gt;st}&lt;/span&gt;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-9\"><td class=\"table-line l-table\"><a href=\"#line-9\">9</a></td><td class=\"code-line l-code\">&lt;span class=&#34;hljs-string&#34;&gt;} &amp;quot;&amp;quot;&amp;quot;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-10\"><td class=\"table-line d-table\"><a href=\"#line-10\"><strong>10</strong></a></td><td class=\"code-line d-code\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;&amp;quot;&amp;quot;f=&amp;quot;true&amp;quot;&amp;quot;&amp;quot;&amp;quot;&lt;/span&gt;\n" +
		"</td></tr></table>"
		;

	private final String simpleCode =
		"<table class=\"code-table\"><tr id=\"line-1\"><td class=\"code-line l-code\"><span class=\"hljs-string\">&quot;astring&quot;</span>\n" +
		"</td></tr><tr id=\"line-2\"><td class=\"code-line d-code\">expression\n" +
		"</td></tr><tr id=\"line-3\"><td class=\"code-line l-code\"><span class=\"hljs-string\">&quot;a <span class=\"hljs-subst\">${  <span class=\"hljs-string\">&quot;string&quot;</span> }</span> <span class=\"hljs-subst\">${<span class=\"hljs-string\">&#x27;c&#x27;</span>}</span> b&quot;</span>\n" +
		"</td></tr><tr id=\"line-4\"><td class=\"code-line d-code\"><span class=\"hljs-string\">&quot;a <span class=\"hljs-subst\">${  <span class=\"hljs-string\">&quot;string <span class=\"hljs-variable\">$var</span> <span class=\"hljs-subst\">${subs}</span>&quot;</span> }</span> b&quot;</span>\n" +
		"</td></tr><tr id=\"line-5\"><td class=\"code-line l-code\"><span class=\"hljs-string\">&quot;&quot;&quot;  <span class=\"hljs-subst\">${</span></span>\n" +
		"</td></tr><tr id=\"line-6\"><td class=\"code-line d-code\"><span class=\"hljs-string\"><span class=\"hljs-subst\">    ${subst}</span></span>\n" +
		"</td></tr><tr id=\"line-7\"><td class=\"code-line l-code\"><span class=\"hljs-string\"><span class=\"hljs-subst\">    <span class=\"hljs-string\">${subst}</span></span></span>\n" +
		"</td></tr><tr id=\"line-8\"><td class=\"code-line d-code\"><span class=\"hljs-string\"><span class=\"hljs-subst\">    ${s<span class=\"hljs-string\">ub</span>st}</span></span>\n" +
		"</td></tr><tr id=\"line-9\"><td class=\"code-line l-code\"><span class=\"hljs-string\">} &quot;&quot;&quot;</span>\n" +
		"</td></tr><tr id=\"line-10\"><td class=\"code-line d-code\"><span class=\"hljs-string\">&quot;&quot;&quot;f=&quot;true&quot;&quot;&quot;&quot;</span>\n" +
		"</td></tr></table>"
		;

	private final String simpleCodeHighlight =
		"<table class=\"code-table\"><tr id=\"line-1\"><td class=\"code-line l-code\"><span class=\"hljs-string\">&quot;astring&quot;</span>\n" +
		"</td></tr><tr id=\"line-2\"><td class=\"code-line d-code\">expression\n" +
		"</td></tr><tr id=\"line-3\" class=\"hll\"><td class=\"code-line\"><span class=\"hljs-string\">&quot;a <span class=\"hljs-subst\">${  <span class=\"hljs-string\">&quot;string&quot;</span> }</span> <span class=\"hljs-subst\">${<span class=\"hljs-string\">&#x27;c&#x27;</span>}</span> b&quot;</span>\n" +
		"</td></tr><tr id=\"line-4\" class=\"hll\"><td class=\"code-line\"><span class=\"hljs-string\">&quot;a <span class=\"hljs-subst\">${  <span class=\"hljs-string\">&quot;string <span class=\"hljs-variable\">$var</span> <span class=\"hljs-subst\">${subs}</span>&quot;</span> }</span> b&quot;</span>\n" +
		"</td></tr><tr id=\"line-5\" class=\"hll\"><td class=\"code-line\"><span class=\"hljs-string\">&quot;&quot;&quot;  <span class=\"hljs-subst\">${</span></span>\n" +
		"</td></tr><tr id=\"line-6\"><td class=\"code-line d-code\"><span class=\"hljs-string\"><span class=\"hljs-subst\">    ${subst}</span></span>\n" +
		"</td></tr><tr id=\"line-7\"><td class=\"code-line l-code\"><span class=\"hljs-string\"><span class=\"hljs-subst\">    <span class=\"hljs-string\">${subst}</span></span></span>\n" +
		"</td></tr><tr id=\"line-8\"><td class=\"code-line d-code\"><span class=\"hljs-string\"><span class=\"hljs-subst\">    ${s<span class=\"hljs-string\">ub</span>st}</span></span>\n" +
		"</td></tr><tr id=\"line-9\"><td class=\"code-line l-code\"><span class=\"hljs-string\">} &quot;&quot;&quot;</span>\n" +
		"</td></tr><tr id=\"line-10\"><td class=\"code-line d-code\"><span class=\"hljs-string\">&quot;&quot;&quot;f=&quot;true&quot;&quot;&quot;&quot;</span>\n" +
		"</td></tr></table>"
		;

	private final String plainCode =
		"<table class=\"code-table\"><tr id=\"line-1\"><td class=\"code-line l-code\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;astring&amp;quot;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-2\"><td class=\"code-line d-code\">expression\n" +
		"</td></tr><tr id=\"line-3\"><td class=\"code-line l-code\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;a &lt;span class=&#34;hljs-subst&#34;&gt;${  &lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;string&amp;quot;&lt;/span&gt; }&lt;/span&gt; &lt;span class=&#34;hljs-subst&#34;&gt;${&lt;span class=&#34;hljs-string&#34;&gt;&amp;#x27;c&amp;#x27;&lt;/span&gt;}&lt;/span&gt; b&amp;quot;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-4\"><td class=\"code-line d-code\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;a &lt;span class=&#34;hljs-subst&#34;&gt;${  &lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;string &lt;span class=&#34;hljs-variable&#34;&gt;$var&lt;/span&gt; &lt;span class=&#34;hljs-subst&#34;&gt;${subs}&lt;/span&gt;&amp;quot;&lt;/span&gt; }&lt;/span&gt; b&amp;quot;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-5\"><td class=\"code-line l-code\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;&amp;quot;&amp;quot;  &lt;span class=&#34;hljs-subst&#34;&gt;${&lt;/span&gt;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-6\"><td class=\"code-line d-code\">&lt;span class=&#34;hljs-string&#34;&gt;&lt;span class=&#34;hljs-subst&#34;&gt;    ${subst}&lt;/span&gt;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-7\"><td class=\"code-line l-code\">&lt;span class=&#34;hljs-string&#34;&gt;&lt;span class=&#34;hljs-subst&#34;&gt;    &lt;span class=&#34;hljs-string&#34;&gt;${subst}&lt;/span&gt;&lt;/span&gt;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-8\"><td class=\"code-line d-code\">&lt;span class=&#34;hljs-string&#34;&gt;&lt;span class=&#34;hljs-subst&#34;&gt;    ${s&lt;span class=&#34;hljs-string&#34;&gt;ub&lt;/span&gt;st}&lt;/span&gt;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-9\"><td class=\"code-line l-code\">&lt;span class=&#34;hljs-string&#34;&gt;} &amp;quot;&amp;quot;&amp;quot;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-10\"><td class=\"code-line d-code\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;&amp;quot;&amp;quot;f=&amp;quot;true&amp;quot;&amp;quot;&amp;quot;&amp;quot;&lt;/span&gt;\n" +
		"</td></tr></table>"
		;

	private final String plainCodeHighlight =
		"<table class=\"code-table\"><tr id=\"line-1\"><td class=\"code-line l-code\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;astring&amp;quot;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-2\"><td class=\"code-line d-code\">expression\n" +
		"</td></tr><tr id=\"line-3\" class=\"hll\"><td class=\"code-line\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;a &lt;span class=&#34;hljs-subst&#34;&gt;${  &lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;string&amp;quot;&lt;/span&gt; }&lt;/span&gt; &lt;span class=&#34;hljs-subst&#34;&gt;${&lt;span class=&#34;hljs-string&#34;&gt;&amp;#x27;c&amp;#x27;&lt;/span&gt;}&lt;/span&gt; b&amp;quot;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-4\" class=\"hll\"><td class=\"code-line\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;a &lt;span class=&#34;hljs-subst&#34;&gt;${  &lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;string &lt;span class=&#34;hljs-variable&#34;&gt;$var&lt;/span&gt; &lt;span class=&#34;hljs-subst&#34;&gt;${subs}&lt;/span&gt;&amp;quot;&lt;/span&gt; }&lt;/span&gt; b&amp;quot;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-5\" class=\"hll\"><td class=\"code-line\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;&amp;quot;&amp;quot;  &lt;span class=&#34;hljs-subst&#34;&gt;${&lt;/span&gt;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-6\"><td class=\"code-line d-code\">&lt;span class=&#34;hljs-string&#34;&gt;&lt;span class=&#34;hljs-subst&#34;&gt;    ${subst}&lt;/span&gt;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-7\"><td class=\"code-line l-code\">&lt;span class=&#34;hljs-string&#34;&gt;&lt;span class=&#34;hljs-subst&#34;&gt;    &lt;span class=&#34;hljs-string&#34;&gt;${subst}&lt;/span&gt;&lt;/span&gt;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-8\"><td class=\"code-line d-code\">&lt;span class=&#34;hljs-string&#34;&gt;&lt;span class=&#34;hljs-subst&#34;&gt;    ${s&lt;span class=&#34;hljs-string&#34;&gt;ub&lt;/span&gt;st}&lt;/span&gt;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-9\"><td class=\"code-line l-code\">&lt;span class=&#34;hljs-string&#34;&gt;} &amp;quot;&amp;quot;&amp;quot;&lt;/span&gt;\n" +
		"</td></tr><tr id=\"line-10\"><td class=\"code-line d-code\">&lt;span class=&#34;hljs-string&#34;&gt;&amp;quot;&amp;quot;&amp;quot;f=&amp;quot;true&amp;quot;&amp;quot;&amp;quot;&amp;quot;&lt;/span&gt;\n" +
		"</td></tr></table>"
		;
}
