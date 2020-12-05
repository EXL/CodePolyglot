package ru.exlmoto.code.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import ru.exlmoto.code.entity.CodeEntity;
import ru.exlmoto.code.helper.ResourceHelper;
import ru.exlmoto.code.helper.UtilityHelper;
import ru.exlmoto.code.highlight.HighlightService;
import ru.exlmoto.code.highlight.enumeration.Mode;
import ru.exlmoto.code.service.DatabaseService;

import java.io.InputStream;

import java.util.Scanner;

@RestController
public class ApiController {
	private final DatabaseService database;
	private final HighlightService highlight;
	private final UtilityHelper util;

	public ApiController(DatabaseService database,
	                     HighlightService highlight,
	                     UtilityHelper util) {
		this.database = database;
		this.highlight = highlight;
		this.util = util;
	}

	@GetMapping(path = "/api/raw/{id}", produces = "text/plain;charset=UTF-8")
	public String raw(@PathVariable(name = "id") String id) {
		return util.getLong(id).flatMap(database::getCodeSnippet).map(CodeEntity::getCodeRaw).orElse(
			"Error: Cannot get code snippet with '" + id + "' id."
		);
	}

	/*
	 * Highlight API usage examples:
	 *  $ cat file.txt | curl --data-binary @- "https://code.exlmoto.ru/api/"
	 *  $ cat file.xml | curl --data-binary @- "https://code.exlmoto.ru/api/?o=xml"
	 *  $ cat src.java | curl --data-binary @- "https://code.exlmoto.ru/api/?o=java"
	 *  $ cat file.xml | curl --data-binary @- "https://code.exlmoto.ru/api/?o=xml;nolines"
	 *  $ cat file.xml | curl --data-binary @- "https://code.exlmoto.ru/api/?o=xml;nolines;15"
	 *  $ cat file.xml | curl --data-binary @- "https://code.exlmoto.ru/api/?o=xml;nolines;15,20"
	 *  $ cat file.xml | curl --data-binary @- "https://code.exlmoto.ru/api/?o=xml;nolines;15,20&h=HighlightJs"
	 *  $ cat file.xml | curl --data-binary @- "https://code.exlmoto.ru/api/?o=xml;nolines;15,20&h=HighlightRouge"
	 *  $ cat file.xml | curl --data-binary @- "https://code.exlmoto.ru/api/?o=xml;nolines;15,20&h=HighlightPygments"
	 *  $ cat file.xml | curl --data-binary @- "https://code.exlmoto.ru/api/?o=xml;nolines;15&h=HighlightPygmentsJython"
	 *
	 * Create simple HTML page:
	 *  $ cat file.xml | curl --data-binary @- "https://code.exlmoto.ru/api/?o=xml" > page.html
	 * Another way:
	 *  $ CONTENT=`cat file.xml | curl --data-binary @- "https://code.exlmoto.ru/api/?o=xml"`
	 *  $ echo "<code class="hljs highlight"><pre>$CONTENT</pre></code>" > page.html
	 */
	@PostMapping(path = "/api", produces = "text/plain;charset=UTF-8")
	public String highlight(InputStream inputDataStream,
	                        @RequestParam(name = "o", required = false, defaultValue = "") String options,
	                        @RequestParam(name = "h", required = false, defaultValue = "HighlightJs") String mode) {
		Scanner scanner = new Scanner(inputDataStream).useDelimiter("\\A");
		if (scanner.hasNext()) {
			String code = scanner.next();
			if (StringUtils.hasText(code) && code.length() < 262144) {
				return highlight.highlightCode(Mode.getMode(mode), util.getCorrectOptions(options), code);
			}
		}
		return "Error: Cannot highlight code snippet.";
	}

	// TODO: CSS
	// TODO: Versions Json
}
