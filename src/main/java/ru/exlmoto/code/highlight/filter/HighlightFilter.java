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

	private final TagCompensator tagCompensator;

	public HighlightFilter(TagCompensator tagCompensator) {
		this.tagCompensator = tagCompensator;
	}

	public String filterCarriageReturn(String source) {
		return source.replaceAll("\r", "");
	}

	public String tableCode(String codeLines, long hStart, long hEnd) {
		return filterBlock(filterLines(codeLines, hStart, hEnd, Filter.table));
	}

	public String tableCodePlain(String codeLines, long hStart, long hEnd) {
		return filterBlock(filterLines(codeLines, hStart, hEnd, Filter.table_plain));
	}

	public String simpleCode(String codeLines, long hStart, long hEnd) {
		return filterBlock(filterLines(codeLines, hStart, hEnd, Filter.simple));
	}

	public String plainCode(String codeLines, long hStart, long hEnd) {
		return filterBlock(filterLines(codeLines, hStart, hEnd, Filter.plain));
	}

	private String filterLines(String codeLines, long hStart, long hEnd, Filter filter) {
		final String compensatedCodeLines = tagCompensator.compensateTags(codeLines).orElse(codeLines);
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new StringReader(compensatedCodeLines));
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

	private String filterBlock(String codeLines) {
		return "<table class=\"code-table\">" + codeLines + "</table>";
	}

	private void filterLinesAux(StringBuilder stringBuilder,
	                            long i, long hStart, long hEnd,
	                            String line, Filter filter) {
		final String codeLine = (filter == Filter.plain || filter == Filter.table_plain) ? Encode.forHtml(line) : line;
		final String codeLineClass = (i % 2 == 0) ? "d-code" : "l-code";
		final String tableLineClass = (i % 2 == 0) ? "d-table" : "l-table";
		final boolean hll = ((i >= hStart) && (i <= hEnd));
		final String id = (i % 10 == 0) ? "<strong>" + i + "</strong>" : String.valueOf(i);
		stringBuilder.append("<tr id=\"line-").append(i);
		stringBuilder.append(hll ? "\" class=\"hll\">" : "\">");
		if (filter == Filter.table || filter == Filter.table_plain) {
			stringBuilder.append("<td class=\"table-line").append(hll ? "" : " " + tableLineClass).append("\">");
			stringBuilder.append("<a href=\"#line-").append(i).append("\">").append(id).append("</a></td>");
		}
		stringBuilder.append("<td class=\"code-line").append(hll ? "" : " " + codeLineClass).append("\">");
		stringBuilder.append(codeLine).append("\n</td></tr>");
	}
}
