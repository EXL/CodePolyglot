package ru.exlmoto.code.highlight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import ru.exlmoto.code.highlight.impl.HighlightJs;
import ru.exlmoto.code.highlight.impl.HighlightPygments;
import ru.exlmoto.code.highlight.impl.HighlightRouge;

import javax.annotation.PostConstruct;

@Service
public class HighlightService {
	private final Logger log = LoggerFactory.getLogger(HighlightService.class);

	private final HighlightJs hightlightJs;
	private final HighlightPygments highlightPygments;
	private final HighlightRouge highlightRouge;

	public HighlightService(HighlightJs hightlightJs,
	                        HighlightPygments highlightPygments,
	                        HighlightRouge highlightRouge) {
		this.hightlightJs = hightlightJs;
		this.highlightPygments = highlightPygments;
		this.highlightRouge = highlightRouge;
	}

	@PostConstruct
	private void getLibraryVersions() {
		log.info("Highlight.js version:\t" + hightlightJs.getLibraryVersion());
		log.info("Pygments version:\t\t" + highlightPygments.getLibraryVersion());
		log.info("Rouge version:\t\t" + highlightRouge.getLibraryVersion());
	}
}
