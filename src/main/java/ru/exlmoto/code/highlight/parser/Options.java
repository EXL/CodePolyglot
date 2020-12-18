/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 EXL <exlmotodev@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
