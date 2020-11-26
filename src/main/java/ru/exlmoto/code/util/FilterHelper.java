package ru.exlmoto.code.util;

import org.springframework.stereotype.Component;

@Component
public class FilterHelper {
	public long getCurrentUnixTime() {
		return System.currentTimeMillis() / 1000L;
	}
}
