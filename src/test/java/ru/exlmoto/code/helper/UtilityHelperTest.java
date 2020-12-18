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

package ru.exlmoto.code.helper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import ru.exlmoto.code.configuration.CodeConfiguration;
import ru.exlmoto.code.service.DatabaseService;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class UtilityHelperTest {
	@Autowired
	private UtilityHelper util;

	@Autowired
	private DatabaseService database;

	@Autowired
	private CodeConfiguration config;

	@Test
	public void testGetCurrentUnixTime() {
		String value = String.valueOf(util.getCurrentUnixTime());
		assertEquals(10, value.length());
		System.out.println("Unix Time: " + value);
	}

	@Test
	public void testGetDateFromTimeStamp() {
		Locale en = Locale.forLanguageTag("en");
		Locale es = Locale.forLanguageTag("es");
		Locale ru = Locale.forLanguageTag("ru");

		System.out.println(util.getDateFromTimestamp(config.getDateFormat(), en, 1580520624L));
		System.out.println(util.getDateFromTimestamp(config.getDateFormat(), en, 1580520624L));
		System.out.println(util.getDateFromTimestamp(config.getDateFormat(), ru, 1580585762L));
		System.out.println(util.getDateFromTimestamp(config.getDateFormat(), ru, 1580585762L));

		System.out.println(util.getDateFromTimestamp(config.getDateFormatFull(), en, 1580520624L));
		System.out.println(util.getDateFromTimestamp(config.getDateFormatFull(), en, 1580585762L));
		System.out.println(util.getDateFromTimestamp(config.getDateFormatFull(), es, 1580585762L));
		System.out.println(util.getDateFromTimestamp(config.getDateFormatFull(), ru, 1580520624L));
	}

	@Test
	public void testGetLong() {
		assertFalse(util.getLong(null).isPresent());
		assertFalse(util.getLong("").isPresent());
		assertFalse(util.getLong(" ").isPresent());
		assertFalse(util.getLong("unknown").isPresent());
		assertFalse(util.getLong("123123123121422112411111").isPresent());
		assertFalse(util.getLong("NaN").isPresent());
		assertFalse(util.getLong("<a href").isPresent());

		util.getLong("-1").ifPresent(num -> assertEquals(-1L, num));
		util.getLong("0").ifPresent(num -> assertEquals(0L, num));
		util.getLong("1").ifPresent(num -> assertEquals(1L, num));
		util.getLong("1231231231214221124").ifPresent(num -> assertEquals(1231231231214221124L, num));
	}

	@Test
	public void testGetCorrectOptions() {
		assertNull(util.getCorrectOptions(null));
		assertEquals("", util.getCorrectOptions(""));
		assertEquals(" ", util.getCorrectOptions(" "));
		assertEquals("unknown", util.getCorrectOptions("unknown"));

		assertEquals("java|nolines", util.getCorrectOptions("java|nolines"));
		assertEquals("java|nolines|30", util.getCorrectOptions("java|nolines|30"));
		assertEquals("java|nolines|15:30", util.getCorrectOptions("java|nolines|15:30"));

		assertEquals("java|nolines", util.getCorrectOptions("java;nolines"));
		assertEquals("java|nolines|30", util.getCorrectOptions("java;nolines;30"));
		assertEquals("java|nolines|15:30", util.getCorrectOptions("java;nolines;15:30"));

		assertEquals("java|nolines", util.getCorrectOptions("java;nolines"));
		assertEquals("java|nolines|30", util.getCorrectOptions("java;nolines|30"));
		assertEquals("java|nolines|15:30", util.getCorrectOptions("java|nolines;15,30"));

		assertEquals("java|nolines", util.getCorrectOptions("java| nolines "));
		assertEquals("java|nolines|30", util.getCorrectOptions("java |nolines | 30"));
		assertEquals("java|nolines|15:30", util.getCorrectOptions("java |nolines; 15, 30"));
	}

	@Test
	public void testGenerateSnippetLinks() {
		List<Pair<String, String>> links =
			util.generateSnippetLinks(database.getCodeSnippets(config.getSnippetCount()), "en");
		for (Pair<String, String> link : links) {
			assertThat(link.getFirst()).isNotBlank();
			assertThat(link.getSecond()).isNotBlank();
			System.out.println(link.getFirst() + "\n" + link.getSecond() + "\n");
		}
	}

	@Test
	public void testGetSimpleSnippetName() {
		assertEquals("01-Feb-2020 08:30:24", util.getSimpleSnippetName(null, "en", 1580520624L));
		assertEquals("01-Feb-2020 08:30:24", util.getSimpleSnippetName("", "en", 1580520624L));
		assertEquals("01-Feb-2020 08:30:24", util.getSimpleSnippetName(" ", "en", 1580520624L));
		assertEquals("unknown", util.getSimpleSnippetName("unknown", "en", 1580520624L));

		assertEquals("Snippet Ti…", util.getSimpleSnippetName("Snippet Title", "en", 1580520624L));
		assertEquals("Long Long …", util.getSimpleSnippetName("Long Long Snippet Title", "en", 1580520624L));
	}
}
