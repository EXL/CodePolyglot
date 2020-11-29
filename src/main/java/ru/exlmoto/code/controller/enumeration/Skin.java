package ru.exlmoto.code.controller.enumeration;

public enum Skin {
	pastorg,
	techno;

	public static Skin checkSkin(String theme) {
		try {
			return Skin.valueOf(theme);
		} catch (IllegalArgumentException ignored) {
			return techno;
		}
	}
}
