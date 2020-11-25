package ru.exlmoto.code.highlight;

public enum Mode {
	HighlightJs,
	HighlightPygments,
	HighlightRouge;

	public static String getName(Mode mode) {
		switch (mode) {
			default:
			case HighlightPygments:
				return "Pygments";
			case HighlightJs:
				return "Highlight.js";
			case HighlightRouge:
				return "Rouge";
		}
	}
}
