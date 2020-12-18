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

package ru.exlmoto.code.highlight;

import org.graalvm.home.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import ru.exlmoto.code.configuration.CodeConfiguration;
import ru.exlmoto.code.helper.UtilityHelper;
import ru.exlmoto.code.highlight.enumeration.Mode;
import ru.exlmoto.code.highlight.filter.HighlightFilter;
import ru.exlmoto.code.highlight.implementation.HighlightJs;
import ru.exlmoto.code.highlight.implementation.HighlightPygments;
import ru.exlmoto.code.highlight.implementation.HighlightRouge;
import ru.exlmoto.code.highlight.parser.Options;
import ru.exlmoto.code.highlight.parser.OptionsParser;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static ru.exlmoto.code.highlight.enumeration.Mode.HighlightJs;
import static ru.exlmoto.code.highlight.enumeration.Mode.HighlightRouge;
import static ru.exlmoto.code.highlight.enumeration.Mode.HighlightPygments;
//import static ru.exlmoto.code.highlight.enumeration.Mode.HighlightPygmentsJython;

@Service
public class HighlightService {
	private final Logger log = LoggerFactory.getLogger(HighlightService.class);

	private final ApplicationContext context;

	private final OptionsParser optionsParser;
	private final HighlightFilter highlightFilter;
	private final UtilityHelper util;
	private final CodeConfiguration config;

	private final HighlightJs highlightJs;
	private final HighlightPygments highlightPygments;
	private final HighlightRouge highlightRouge;

	private final Map<Mode, Pair<String, String>> versions;

	public HighlightService(ApplicationContext context,
	                        OptionsParser optionsParser,
	                        HighlightFilter highlightFilter,
	                        UtilityHelper util,
	                        CodeConfiguration config,
	                        HighlightJs highlightJs,
	                        HighlightPygments highlightPygments,
	                        HighlightRouge highlightRouge) {
		this.context = context;
		this.optionsParser = optionsParser;
		this.highlightFilter = highlightFilter;
		this.util = util;
		this.config = config;
		this.highlightJs = highlightJs;
		this.highlightPygments = highlightPygments;
		this.highlightRouge = highlightRouge;

		this.versions = new HashMap<>();
	}

	@PostConstruct
	private void getAllVersions() {
		versions.put(HighlightJs,
			Pair.of(highlightJs.getLanguageVersion(), highlightJs.getLibraryVersion()));
		log.info(String.format("GraalVM JavaScript version '%s' and Highlight.js version '%s' loaded.",
			versions.get(HighlightJs).getFirst(), versions.get(HighlightJs).getSecond()));

		versions.put(HighlightRouge,
			Pair.of(highlightRouge.getLanguageVersion(), highlightRouge.getLibraryVersion()));
		log.info(String.format("GraalVM Ruby version '%s' and Rouge version '%s' loaded.",
			versions.get(HighlightRouge).getFirst(), versions.get(HighlightRouge).getSecond()));

//		highlightPygments.setUseJython(false);
		versions.put(HighlightPygments,
			Pair.of(highlightPygments.getLanguageVersion(), highlightPygments.getLibraryVersion()));
		log.info(String.format("GraalVM Python version '%s' and Pygments version '%s' loaded.",
			versions.get(HighlightPygments).getFirst(), versions.get(HighlightPygments).getSecond()));

//		highlightPygments.setUseJython(true);
//		versions.put(HighlightPygmentsJython,
//			Pair.of(highlightPygments.getLanguageVersion(), highlightPygments.getLibraryVersion()));
//		log.info(String.format("Jython Python version '%s' and Pygments version '%s' loaded.",
//			versions.get(HighlightPygmentsJython).getFirst(), versions.get(HighlightPygmentsJython).getSecond()));

		log.info(String.format("GraalVM version '%s'.", getApplicationVersions().getFirst()));
		log.info(String.format("Code Polyglot version '%s'.", getApplicationVersions().getSecond()));

//		checkCssStyles();
	}

	private void checkCssStyles() {
		highlightJs.generateCssStyle("tomorrow")
			.ifPresent((css) -> log.info(HighlightJs.name() + ":\n" + css + "\n---\n"));
		highlightRouge.generateCssStyle("base16")
			.ifPresent((css) -> log.info(HighlightRouge.name() + ":\n" + css + "\n---\n"));
//		highlightPygments.setUseJython(false);
		highlightPygments.generateCssStyle("vs")
			.ifPresent((css) -> log.info(HighlightPygments.name() + ":\n" + css + "\n---\n"));
//		highlightPygments.setUseJython(true);
//		highlightPygments.generateCssStyle("vim")
//			.ifPresent((css) -> log.info(HighlightPygmentsJython.name() + ":\n" + css + "\n---\n"));
	}

	public String highlightCode(Mode mode, String options, String code) {
		final Options settings = optionsParser.parseOptions(options);
		final long hStart = settings.gethStart();
		final long hEnd = settings.gethEnd();
		final String filteredCode = highlightFilter.filterCarriageReturn(code);
		highlightFilter.setEscape(false);
		return (settings.isHighlightDisabled()) ?
			(settings.isTable()) ?
				highlightFilter.tableCodePlain(filteredCode, hStart, hEnd) :
				highlightFilter.plainCode(filteredCode, hStart, hEnd) :
			(settings.isTable()) ?
				highlightFilter.tableCode(highlightCodeAux(mode, filteredCode, settings), hStart, hEnd) :
				highlightFilter.simpleCode(highlightCodeAux(mode, filteredCode, settings), hStart, hEnd);
	}

	public Pair<String, String> getApplicationVersions() {
		final String graalVmVersion = Version.getCurrent().toString();

		String applicationVersion = "unknown";
		try {
			applicationVersion = context.getBean(BuildProperties.class).getVersion();
		} catch (RuntimeException re) {
			log.error(String.format("Cannot get application version: '%s'.", re.getLocalizedMessage()), re);
		}

		String applicationRevision = "unknown";
		try {
			applicationRevision = context.getBean(BuildProperties.class).get("revision");
		} catch (RuntimeException re) {
			log.error(String.format("Cannot get application revision: '%s'.", re.getLocalizedMessage()), re);
		}

		String buildDateTime = "unknown";
		try {
			buildDateTime = util.getDateFromTimestamp(config.getDateFormat(), Locale.forLanguageTag("en"),
				context.getBean(BuildProperties.class).getTime().getEpochSecond());
		} catch (RuntimeException re) {
			log.error(String.format("Cannot get build date time: '%s'.", re.getLocalizedMessage()), re);
		}

		return Pair.of(graalVmVersion, applicationVersion + ", " + applicationRevision + ", " + buildDateTime);
	}

	public Map<Mode, Pair<String, String>> getLibraryVersions() {
		return versions;
	}

	private String highlightCodeAux(Mode mode, String code, Options options) {
		RuntimeException error = new RuntimeException("Error while highlighting code snippet.");
		try {
			switch (mode) {
				default:
				case HighlightJs: {
					return highlightJs.renderHtmlFromCode(options.getLanguage(), code).orElseThrow(() -> error);
				}
				case HighlightRouge: {
					return highlightRouge.renderHtmlFromCode(options.getLanguage(), code).orElseThrow(() -> error);
				}
				case HighlightPygments: {
//					highlightPygments.setUseJython(false);
					return highlightPygments.renderHtmlFromCode(options.getLanguage(), code).orElseThrow(() -> error);
				}
//				case HighlightPygmentsJython: {
//					highlightPygments.setUseJython(true);
//					return highlightPygments.renderHtmlFromCode(options.getLanguage(), code).orElseThrow(() -> error);
//				}
			}
		} catch (RuntimeException re) {
			highlightFilter.setEscape(true);
			return code;
		}
	}
}
/*
// Stub service class.
@Service
public class HighlightService {
	private final Map<Mode, Pair<String, String>> versions;

	public HighlightService() {
		this.versions = new HashMap<>();
		versions.put(HighlightJs, Pair.of("20.3.0", "10.0.1"));
		versions.put(HighlightRouge, Pair.of("2.6.6", "3.1.0"));
		versions.put(HighlightPygments, Pair.of("3.8.0", "2.7.2"));
//		versions.put(HighlightPygmentsJython, Pair.of("2.7.2", "2.6.2"));
	}

	public Map<Mode, Pair<String, String>> getLibraryVersions() {
		return versions;
	}

	public Pair<String, String> getApplicationVersions() {
		return Pair.of(Version.getCurrent().toString(), "1.0.0, unknown, unknown");
	}

	public String highlightCode(Mode mode, String options, String code) {
		return code;
	}
}
*/
