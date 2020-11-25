package ru.exlmoto.code.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.exlmoto.code.polyglot.PolyglotService;

@RestController
public class CodeController {
	private final PolyglotService polyglotService;

	public CodeController(PolyglotService polyglotService) {
		this.polyglotService = polyglotService;
	}

	@GetMapping(value = "/", produces = "text/plain")
	public String index() {
		String JS = "\nJS: " + polyglotService.executeJavaScript("hljs.highlightAuto('<span>Hello World!</span>').value").orElse("ERROR!");
		String PY = "\nPY: " + polyglotService.executePython("HtmlFormatter().get_style_defs('.highlight')").orElse("ERROR!");
		String RB = "\nRB: " + polyglotService.executeRuby("Rouge::Theme.find('base16.light').render(scope: '.highlight')").orElse("ERROR!");

		return JS + "\n" + PY + "\n" + RB;
	}
}
