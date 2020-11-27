package ru.exlmoto.code.highlight.filter;

import org.owasp.encoder.Encode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import ru.exlmoto.code.highlight.enumeration.Filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

@Component
public class HighlightFilter {
	private final Logger log = LoggerFactory.getLogger(HighlightFilter.class);

	public String filterCarriageReturn(String source) {
		return source.replaceAll("\r", "");
	}

	public String tableCode(String codeLines) {
		return filterBlock(filterLines(codeLines, 0, 0, Filter.table), Filter.table);
	}

	public String tableCodeHighlight(String codeLines, int hStart, int hEnd) {
		return filterBlock(filterLines(codeLines, hStart, hEnd, Filter.table), Filter.table);
	}

	public String simpleCode(String codeLines) {
		return filterBlock(filterLines(codeLines, 0, 0, Filter.simple), Filter.simple);
	}

	public String simpleCodeHighlight(String codeLines, int hStart, int hEnd) {
		return filterBlock(filterLines(codeLines, hStart, hEnd, Filter.simple), Filter.simple);
	}

	public String plainCode(String codeLines) {
		return filterBlock(filterLines(codeLines, 0, 0, Filter.plain), Filter.plain);
	}

	public String plainCodeHighlight(String codeLines, int hStart, int hEnd) {
		return filterBlock(filterLines(codeLines, hStart, hEnd, Filter.plain), Filter.plain);
	}

	protected String filterLines(String codeLines, int hStart, int hEnd, Filter filter) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new StringReader(codeLines));
			String line = reader.readLine();
			int i = 1;
			while (line != null) {
				filterLinesAux(sb, i, hStart, hEnd, line, filter);
				i++;
				line = reader.readLine();
			}
		} catch (IOException ioe) {
			log.error(String.format("Error in Buffered Reader for String: '%s'.", ioe.getLocalizedMessage()), ioe);
		}
		return sb.toString();
	}

	protected String filterBlock(String codeLines, Filter filter) {
		switch (filter) {
			default:
			case table:
				return "<table class=\"code-table\">" + codeLines + "</table>";
			case simple:
			case plain:
				return "<pre><code>" + codeLines + "</pre></code>";
		}
	}

	private void filterLinesAux(StringBuilder stringBuilder, int i, int hStart, int hEnd, String line, Filter filter) {
		final String codeLine = (filter == Filter.plain) ? Encode.forHtml(line) : line;
		switch (filter) {
			default:
			case table: {
				String id = (i % 10 == 0) ? "<strong>" + i + "</strong>" : String.valueOf(i);
				stringBuilder.append("<tr id=\"line-").append(i);
				stringBuilder.append(((i >= hStart) && (i <= hEnd)) ? "\" class=\"hll\">" : "\">");
				stringBuilder.append("<td class=\"code-table gl\"><pre>");
				stringBuilder.append("<a href=\"#line-").append(i).append("\">").append(id).append("</a></pre></td>");
				stringBuilder.append("<td class=\"code-table code\"><pre>").append(codeLine).append("</pre></td></tr>");
				break;
			}
			case simple:
			case plain: {
				stringBuilder.append((i == hStart) ? "<span class=\"hll\">" + codeLine : codeLine);
				stringBuilder.append((i == hEnd) ? "</span>" : "");
				break;
			}
		}
	}
}