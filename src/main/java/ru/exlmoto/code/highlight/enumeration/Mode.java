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

package ru.exlmoto.code.highlight.enumeration;

import org.springframework.util.StringUtils;

import ru.exlmoto.code.controller.enumeration.Skin;

public enum Mode {
	HighlightJs,
	HighlightRouge,
//	HighlightPygmentsJython,
//	HighlightPygments
	;

	public static String getName(Mode mode) {
		switch (mode) {
			default:
			case HighlightJs:
				return "Highlight.js";
			case HighlightRouge:
				return "Rouge";
//			case HighlightPygments:
//			case HighlightPygmentsJython:
//				return "Pygments";
		}
	}

	public static String getVm(Mode mode) {
		switch (mode) {
			default:
			case HighlightJs:
			case HighlightRouge:
//			case HighlightPygments:
				return "GraalVM";
//			case HighlightPygmentsJython:
//				return "Jython";
		}
	}

	public static String getSpeed(Mode mode) {
		switch (mode) {
			default:
			case HighlightJs:
				return "code.table.fast";
			case HighlightRouge:
				return "code.table.moderate";
//			case HighlightPygmentsJython:
//				return "code.table.slow";
//			case HighlightPygments:
//				return "code.table.very.slow";
		}
	}

	public static String getLang(Mode mode) {
		switch (mode) {
			default:
			case HighlightJs:
				return "JavaScript";
			case HighlightRouge:
				return "Ruby";
//			case HighlightPygments:
//			case HighlightPygmentsJython:
//				return "Python";
		}
	}

	public static Mode getMode(String name) {
		if (StringUtils.hasText(name)) {
			try {
				return Mode.valueOf(name);
			} catch (IllegalArgumentException ignored) { }
		}
		return HighlightJs;
	}

	public static String getCss(Mode mode, Skin theme) {
		switch (mode) {
			case HighlightRouge:
//			case HighlightPygments:
//			case HighlightPygmentsJython:
				return "static/css/" + theme.name() + "/merged.css";
		}
		return "static/css/" + theme.name() + "/hjs.css";
	}
}
