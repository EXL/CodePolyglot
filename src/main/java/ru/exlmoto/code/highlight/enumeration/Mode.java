package ru.exlmoto.code.highlight.enumeration;

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
				return "code.fast";
			case HighlightRouge:
				return "code.moderate";
			case HighlightPygmentsJython:
				return "code.slow";
			case HighlightPygments:
				return "code.very_slow";
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
		try {
			return Mode.valueOf(name);
		} catch (IllegalArgumentException ignored) {
			return HighlightJs;
		}
	}
}
