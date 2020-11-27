package ru.exlmoto.code.highlight.enumeration;

public enum Mode {
	HighlightJs,
	HighlightRouge,
	HighlightPygments;

	public static String getName(Mode mode) {
		switch (mode) {
			default:
			case HighlightJs:
				return "Highlight.js";
			case HighlightPygments:
				return "Pygments";
			case HighlightRouge:
				return "Rouge";
		}
	}

	public static Mode getMode(String name) {
		switch (name) {
			default:
			case "Highlight.js":
				return HighlightJs;
			case "Pygments":
				return HighlightPygments;
			case "Rouge":
				return HighlightRouge;
		}
	}
}
