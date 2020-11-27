package ru.exlmoto.code.highlight.parser;

public class Options {
	public static final int MAX_OPTION_STRING_LENGTH = 64;
	public static final String GUESS_LEXER_OPTION = "auto";
	public static final String DISABLE_HIGHLIGHT_OPTION = "";
	public static final String DISABLE_TABLE_OPTION = "nolines";

	private String language;
	private boolean table;
	private long hStart;
	private long hEnd;

	public Options() {
		this.language = DISABLE_HIGHLIGHT_OPTION;
		this.table = true;
		this.hStart = 0L;
		this.hEnd = 0L;
	}

	public boolean isHighlightDisabled() {
		return language.equals(DISABLE_HIGHLIGHT_OPTION);
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean isTable() {
		return table;
	}

	public void setTable(boolean table) {
		this.table = table;
	}

	public long gethStart() {
		return hStart;
	}

	public void sethStart(long hStart) {
		this.hStart = hStart;
	}

	public long gethEnd() {
		return hEnd;
	}

	public void sethEnd(long hEnd) {
		this.hEnd = hEnd;
	}

	@Override
	public String toString() {
		return "Options{" +
			"language='" + language + '\'' +
			", table=" + table +
			", hStart=" + hStart +
			", hEnd=" + hEnd +
			'}';
	}
}
