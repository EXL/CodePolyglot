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
				return "Highlight.js/GraalVM (fast)";
			case HighlightRouge:
				return "Rouge/GraalVM (moderate)";
			case HighlightPygmentsJython:
				return "Pygments/Jython (slow)";
			case HighlightPygments:
				return "Pygments/GraalVM (very slow)";
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
