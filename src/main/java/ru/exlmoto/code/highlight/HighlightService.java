package ru.exlmoto.code.highlight;

import org.graalvm.home.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import ru.exlmoto.code.highlight.enumeration.Mode;
import ru.exlmoto.code.highlight.filter.HighlightFilter;
import ru.exlmoto.code.highlight.implementation.HighlightJs;
import ru.exlmoto.code.highlight.implementation.HighlightPygments;
import ru.exlmoto.code.highlight.implementation.HighlightRouge;
import ru.exlmoto.code.highlight.parser.Options;
import ru.exlmoto.code.highlight.parser.OptionsParser;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

import static ru.exlmoto.code.highlight.enumeration.Mode.HighlightJs;
import static ru.exlmoto.code.highlight.enumeration.Mode.HighlightRouge;
import static ru.exlmoto.code.highlight.enumeration.Mode.HighlightPygments;
import static ru.exlmoto.code.highlight.enumeration.Mode.HighlightPygmentsJython;

@Service
public class HighlightService {
	private final Logger log = LoggerFactory.getLogger(HighlightService.class);

	private final OptionsParser optionsParser;
	private final HighlightFilter highlightFilter;

	private final HighlightJs highlightJs;
	private final HighlightPygments highlightPygments;
	private final HighlightRouge highlightRouge;

	private final Map<Mode, Pair<String, String>> versions;

	public HighlightService(OptionsParser optionsParser,
	                        HighlightFilter highlightFilter,
	                        HighlightJs highlightJs,
	                        HighlightPygments highlightPygments,
	                        HighlightRouge highlightRouge) {
		this.optionsParser = optionsParser;
		this.highlightFilter = highlightFilter;
		this.highlightJs = highlightJs;
		this.highlightPygments = highlightPygments;
		this.highlightRouge = highlightRouge;

		versions = new HashMap<>();
	}

	@PostConstruct
	private void getAllVersions() {
		log.info(String.format("GraalVM version '%s'.", getGraalVMVersion()));

		versions.put(HighlightJs,
			Pair.of(highlightJs.getLanguageVersion(), highlightJs.getLibraryVersion()));
		log.info(String.format("GraalVM JavaScript version '%s' and Highlight.js version '%s' loaded.",
			versions.get(HighlightJs).getFirst(), versions.get(HighlightJs).getSecond()));

		versions.put(HighlightRouge,
			Pair.of(highlightRouge.getLanguageVersion(), highlightRouge.getLibraryVersion()));
		log.info(String.format("GraalVM Ruby version '%s' and Rouge version '%s' loaded.",
			versions.get(HighlightRouge).getFirst(), versions.get(HighlightRouge).getSecond()));

		highlightPygments.setUseJython(false);
		versions.put(HighlightPygments,
			Pair.of(highlightPygments.getLanguageVersion(), highlightPygments.getLibraryVersion()));
		log.info(String.format("GraalVM Python version '%s' and Pygments version '%s' loaded.",
			versions.get(HighlightPygments).getFirst(), versions.get(HighlightPygments).getSecond()));

		highlightPygments.setUseJython(true);
		versions.put(HighlightPygmentsJython,
			Pair.of(highlightPygments.getLanguageVersion(), highlightPygments.getLibraryVersion()));
		log.info(String.format("Jython Python version '%s' and Pygments version '%s' loaded.",
			versions.get(HighlightPygmentsJython).getFirst(), versions.get(HighlightPygmentsJython).getSecond()));
	}

	public String highlightCode(Mode mode, String options, String code) {
		final Options settings = optionsParser.parseOptions(options);
		final long hStart = settings.gethStart();
		final long hEnd = settings.gethEnd();
		final String filteredCode = highlightFilter.filterCarriageReturn(code);
		return (settings.isHighlightDisabled()) ?
			(settings.isTable()) ?
				highlightFilter.tableCodePlain(filteredCode, hStart, hEnd) :
				highlightFilter.plainCode(filteredCode, hStart, hEnd) :
			(settings.isTable()) ?
				highlightFilter.tableCode(highlightCodeAux(mode, filteredCode, settings), hStart, hEnd) :
				highlightFilter.simpleCode(highlightCodeAux(mode, filteredCode, settings), hStart, hEnd);
	}

	public String getGraalVMVersion() {
		return Version.getCurrent().toString();
	}

	public Map<Mode, Pair<String, String>> getVersions() {
		return versions;
	}

	private String highlightCodeAux(Mode mode, String code, Options options) {
		switch (mode) {
			default:
			case HighlightJs: {
				return highlightJs.renderHtmlFromCode(options.getLanguage(), code)
					.orElse(highlightFilter.plainCodeLines(code, options.gethStart(), options.gethEnd()));
			}
			case HighlightRouge: {
				return highlightRouge.renderHtmlFromCode(options.getLanguage(), code)
					.orElse(highlightFilter.plainCodeLines(code, options.gethStart(), options.gethEnd()));
			}
			case HighlightPygments: {
				highlightPygments.setUseJython(false);
				return highlightPygments.renderHtmlFromCode(options.getLanguage(), code)
					.orElse(highlightFilter.plainCodeLines(code, options.gethStart(), options.gethEnd()));
			}
			case HighlightPygmentsJython: {
				highlightPygments.setUseJython(true);
				return highlightPygments.renderHtmlFromCode(options.getLanguage(), code)
					.orElse(highlightFilter.plainCodeLines(code, options.gethStart(), options.gethEnd()));
			}
		}
	}
}
