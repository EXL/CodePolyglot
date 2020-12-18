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

package ru.exlmoto.code.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import ru.exlmoto.code.configuration.CodeConfiguration;
import ru.exlmoto.code.controller.enumeration.Skin;
import ru.exlmoto.code.entity.CodeEntity;
import ru.exlmoto.code.helper.ResourceHelper;
import ru.exlmoto.code.helper.UtilityHelper;
import ru.exlmoto.code.highlight.HighlightService;
import ru.exlmoto.code.highlight.enumeration.Mode;
import ru.exlmoto.code.service.DatabaseService;

import java.io.InputStream;
import java.io.UncheckedIOException;

import java.util.Scanner;

@RestController
public class ApiController {
	private final Logger log = LoggerFactory.getLogger(ApiController.class);

	private final CodeConfiguration config;
	private final DatabaseService database;
	private final HighlightService highlight;
	private final ResourceHelper resource;
	private final UtilityHelper util;

	public ApiController(CodeConfiguration config,
	                     DatabaseService database,
	                     HighlightService highlight,
	                     ResourceHelper resource,
	                     UtilityHelper util) {
		this.config = config;
		this.database = database;
		this.highlight = highlight;
		this.resource = resource;
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
		Scanner scanner = new Scanner(inputDataStream).useDelimiter("\\A"); // https://stackoverflow.com/a/5445161
		if (scanner.hasNext()) {
			String code = scanner.next();
			if (StringUtils.hasText(code) && code.length() < config.getSnippetMaxLength()) {
				return highlight.highlightCode(Mode.getMode(mode), util.getCorrectOptions(options), code);
			}
		}
		scanner.close();
		return "Error: Cannot highlight code snippet.";
	}

	/*
	 * Highlight CSS usage examples:
	 * $ curl "https://code.exlmoto.ru/api/css"
	 * $ curl "https://code.exlmoto.ru/api/css?skin=pastorg"
	 * $ curl "https://code.exlmoto.ru/api/css?skin=pastorg&mode=HighlightRouge"
	 * $ curl "https://code.exlmoto.ru/api/css?skin=techno&mode=HighlightPygments"
	 */
	@GetMapping(path = "/api/css", produces = "text/css;charset=UTF-8")
	public String css(@RequestParam(name = "skin", required = false, defaultValue = "techno") String skin,
	                  @RequestParam(name = "mode", required = false, defaultValue = "HighlightJs") String mode) {
		try {
			return resource.readFileToString("classpath:" + Mode.getCss(Mode.valueOf(mode), Skin.valueOf(skin)));
		} catch (IllegalArgumentException | UncheckedIOException ignored) { }
		return "Error: Cannot get CSS from resources.";
	}

	@GetMapping(path = "/api/versions", produces = "application/json;charset=UTF-8")
	public String versions() {
		final ObjectMapper mapper = new ObjectMapper();
		final ObjectNode root = mapper.createObjectNode();
		highlight.getLibraryVersions().forEach((k, v) -> {
			final ObjectNode child = mapper.createObjectNode();
			child.put("language", v.getFirst());
			child.put("library", v.getSecond());
			root.set(k.name(), child);
		});
		root.put("GraalVM", highlight.getApplicationVersions().getFirst());
		root.put("CodePolyglot", highlight.getApplicationVersions().getSecond());

		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
		} catch (JsonProcessingException jpe) {
			final String error = String.format("Cannot generate JSON version information: '%s'.", jpe.getMessage());
			log.error(error, jpe);
			return "{ \"error\" : \"" + error + "\" }";
		}
	}
}
