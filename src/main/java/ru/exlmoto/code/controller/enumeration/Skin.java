package ru.exlmoto.code.controller.enumeration;

import org.springframework.util.StringUtils;

public enum Skin {
	pastorg,
	techno;

	public static Skin checkSkin(String theme) {
		if (StringUtils.hasText(theme)) {
			try {
				return Skin.valueOf(theme);
			} catch (IllegalArgumentException ignored) { }
		}
		return techno;
	}
}
