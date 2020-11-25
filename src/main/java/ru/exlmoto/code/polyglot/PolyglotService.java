package ru.exlmoto.code.polyglot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import ru.exlmoto.code.polyglot.impl.PolyglotJavaScript;
import ru.exlmoto.code.polyglot.impl.PolyglotPython;
import ru.exlmoto.code.polyglot.impl.PolyglotRuby;

import javax.annotation.PostConstruct;

import java.util.Optional;

//@Service
public class PolyglotService {
	private final Logger log = LoggerFactory.getLogger(PolyglotService.class);

	private final PolyglotJavaScript polyglotJavaScript;
	private final PolyglotPython polyglotPython;
	private final PolyglotRuby polyglotRuby;

	public PolyglotService(PolyglotJavaScript polyglotJavaScript,
	                       PolyglotPython polyglotPython,
	                       PolyglotRuby polyglotRuby) {
		this.polyglotJavaScript = polyglotJavaScript;
		this.polyglotPython = polyglotPython;
		this.polyglotRuby = polyglotRuby;
	}

	@PostConstruct
	private void getLanguageVersions() {
		log.info(String.format("=> GraalVM JavaScript version '%s' loaded.", polyglotJavaScript.getLanguageVersion()));
		log.info(String.format("=> GraalVM Python version '%s' loaded.", polyglotPython.getLanguageVersion()));
		log.info(String.format("=> GraalVM Ruby version '%s' loaded.", polyglotRuby.getLanguageVersion()));
	}

	public Optional<String> executeJavaScript(String code) {
		return polyglotJavaScript.execute(code);
	}

	public Optional<String> executePython(String code) {
		return polyglotPython.execute(code);
	}

	public Optional<String> executeRuby(String code) {
		return polyglotRuby.execute(code);
	}
}
