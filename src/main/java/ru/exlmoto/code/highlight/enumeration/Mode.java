package ru.exlmoto.code.highlight.enumeration;

import org.springframework.util.StringUtils;

import ru.exlmoto.code.controller.enumeration.Skin;

public enum Mode {
	HighlightJs,
	HighlightRouge,
	HighlightPygmentsJython,
	HighlightPygments;

	public static String getName(Mode mode) {
		switch (mode) {
			default:
			case HighlightJs:
				return "Highlight.js";
			case HighlightRouge:
				return "Rouge";
			case HighlightPygments:
			case HighlightPygmentsJython:
				return "Pygments";
		}
	}

	public static String getVm(Mode mode) {
		switch (mode) {
			default:
			case HighlightJs:
			case HighlightRouge:
			case HighlightPygments:
				return "GraalVM";
			case HighlightPygmentsJython:
				return "Jython";
		}
	}

	public static String getSpeed(Mode mode) {
		switch (mode) {
			default:
			case HighlightJs:
				return "code.table.fast";
			case HighlightRouge:
				return "code.table.moderate";
			case HighlightPygmentsJython:
				return "code.table.slow";
			case HighlightPygments:
				return "code.table.very.slow";
		}
	}

	public static String getLang(Mode mode) {
		switch (mode) {
			default:
			case HighlightJs:
				return "JavaScript";
			case HighlightRouge:
				return "Ruby";
			case HighlightPygments:
			case HighlightPygmentsJython:
				return "Python";
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
			case HighlightPygments:
			case HighlightPygmentsJython:
				return "static/css/" + theme.name() + "/merged.css";
		}
		return "static/css/" + theme.name() + "/hjs.css";
	}
}
