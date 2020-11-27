package ru.exlmoto.code.highlight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import ru.exlmoto.code.highlight.enumeration.Mode;
import ru.exlmoto.code.highlight.enumeration.Options;
import ru.exlmoto.code.highlight.implementation.HighlightJs;
import ru.exlmoto.code.highlight.implementation.HighlightPygments;
import ru.exlmoto.code.highlight.implementation.HighlightRouge;
import ru.exlmoto.code.helper.FilterHelper;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

@Service
public class HighlightService {
	private final Logger log = LoggerFactory.getLogger(HighlightService.class);

	private final FilterHelper filter;
	private final HighlightJs highlightJs;
	private final HighlightPygments highlightPygments;
	private final HighlightRouge highlightRouge;

	public HighlightService(FilterHelper filter, HighlightJs highlightJs,
	                        HighlightPygments highlightPygments,
	                        HighlightRouge highlightRouge) {
		this.filter = filter;
		this.highlightJs = highlightJs;
		this.highlightPygments = highlightPygments;
		this.highlightRouge = highlightRouge;
	}

	@PostConstruct
	private void getLibraryVersions() {
		log.info(String.format("GraalVM JavaScript version '%s' and Highlight.js version '%s' loaded.",
			highlightJs.getLanguageVersion(), highlightJs.getLibraryVersion()));
		log.info(String.format("GraalVM Python version '%s' and Pygments version '%s' loaded.",
			highlightPygments.getLanguageVersion(), highlightPygments.getLibraryVersion()));
		log.info(String.format("GraalVM Ruby version '%s' and Rouge version '%s' loaded.",
			highlightRouge.getLanguageVersion(), highlightRouge.getLibraryVersion()));
	}

	public String renderHtmlFromCode(Mode mode, String options, String code) {
		// TODO Parse this.
		final Map<Options, String> optionsMap = new HashMap<>();
		optionsMap.put(Options.lang, options);

		final String renderedCode;

		switch (mode) {
			default:
			case HighlightPygments: {
				renderedCode = highlightPygments.renderHtmlFromCode(optionsMap, code);
				break;
			}
			case HighlightJs: {
				renderedCode = highlightJs.renderHtmlFromCode(optionsMap, code);
				break;
			}
			case HighlightRouge: {
				renderedCode = highlightRouge.renderHtmlFromCode(optionsMap, code);
			}
		}

		return renderedCode;
	}
}
