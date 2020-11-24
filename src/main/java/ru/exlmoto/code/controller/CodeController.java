package ru.exlmoto.code.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.graalvm.polyglot.*;

@RestController
public class CodeController {
	Context polyglot = Context.create();

	@GetMapping(value = "/", produces = "text/plain")
	public String test() {
		String js = "JS: ";
		return "";
	}
}
