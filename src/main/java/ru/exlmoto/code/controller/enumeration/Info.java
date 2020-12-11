package ru.exlmoto.code.controller.enumeration;

import org.springframework.util.StringUtils;

public enum Info {
	empty,
	databaseError,
	deleteOk,
	deleteError;

	public static String getDescription(String info) {
		if (StringUtils.hasText(info)) {
			try {
				switch (Info.valueOf(info)) {
					case empty:
						return "code.info.empty";
					case databaseError:
						return "code.info.database";
					case deleteOk:
						return "code.info.delete.ok";
					case deleteError:
						return "code.info.delete.error";
				}
			} catch (IllegalArgumentException ignored) {
			}
		}
		return "code.info.unknown";
	}
}
