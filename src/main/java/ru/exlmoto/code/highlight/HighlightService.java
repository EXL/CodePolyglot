package ru.exlmoto.code.highlight;

import org.graalvm.home.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import ru.exlmoto.code.highlight.enumeration.Mode;
import ru.exlmoto.code.highlight.filter.HighlightFilter;
import ru.exlmoto.code.highlight.implementation.HighlightJs;
import ru.exlmoto.code.highlight.implementation.HighlightPygments;
import ru.exlmoto.code.highlight.implementation.HighlightRouge;
import ru.exlmoto.code.highlight.parser.Options;
import ru.exlmoto.code.highlight.parser.OptionsParser;

import javax.annotation.PostConstruct;

@Service
public class HighlightService {
	private final Logger log = LoggerFactory.getLogger(HighlightService.class);

	private final OptionsParser optionsParser;
	private final HighlightFilter highlightFilter;

	private final HighlightJs highlightJs;
	private final HighlightPygments highlightPygments;
	private final HighlightRouge highlightRouge;

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
	}

	@PostConstruct
	private void getVersions() {
		log.info(String.format("GraalVM JavaScript version '%s' and Highlight.js version '%s' loaded.",
			highlightJs.getLanguageVersion(), highlightJs.getLibraryVersion()));
		log.info(String.format("GraalVM Python version '%s' and Pygments version '%s' loaded.",
			highlightPygments.getLanguageVersion(), highlightPygments.getLibraryVersion()));
		log.info(String.format("GraalVM Ruby version '%s' and Rouge version '%s' loaded.",
			highlightRouge.getLanguageVersion(), highlightRouge.getLibraryVersion()));
		log.info(String.format("GraalVM version '%s'.", Version.getCurrent().toString()));
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
				return highlightPygments.renderHtmlFromCode(options.getLanguage(), code)
					.orElse(highlightFilter.plainCodeLines(code, options.gethStart(), options.gethEnd()));
			}
		}
	}
}
