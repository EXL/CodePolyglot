package ru.exlmoto.code.polyglot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.exlmoto.code.polyglot.impl.JavaScriptPolyglot;
import ru.exlmoto.code.polyglot.impl.PythonPolyglot;
import ru.exlmoto.code.polyglot.impl.RubyPolyglot;

import java.util.Optional;

@Service
public class PolyglotService {
	private final Logger log = LoggerFactory.getLogger(PolyglotService.class);

	private final JavaScriptPolyglot javaScriptPolyglot;
	private final PythonPolyglot pythonPolyglot;
	private final RubyPolyglot rubyPolyglot;

	public PolyglotService(JavaScriptPolyglot javaScriptPolyglot,
	                       PythonPolyglot pythonPolyglot,
	                       RubyPolyglot rubyPolyglot) {
		this.javaScriptPolyglot = javaScriptPolyglot;
		this.pythonPolyglot = pythonPolyglot;
		this.rubyPolyglot = rubyPolyglot;

		hotspotPolyglot();
	}

	private void hotspotPolyglot() {
		log.info("Loaded GraalVM JavaScript:\t" + javaScriptPolyglot.loadRequiredLibrariesAndGetVersion());
		log.info("Loaded GraalVM Python:\t\t" + pythonPolyglot.loadRequiredLibrariesAndGetVersion());
		log.info("Loaded GraalVM Ruby:\t\t" + rubyPolyglot.loadRequiredLibrariesAndGetVersion());
	}

	public Optional<String> executeJavaScript(String code) {
		return javaScriptPolyglot.execute(code);
	}

	public Optional<String> executePython(String code) {
		return pythonPolyglot.execute(code);
	}

	public Optional<String> executeRuby(String code) {
		return rubyPolyglot.execute(code);
	}
}
