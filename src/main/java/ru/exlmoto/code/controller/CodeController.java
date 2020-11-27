package ru.exlmoto.code.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CookieValue;

import ru.exlmoto.code.form.CodeForm;
import ru.exlmoto.code.highlight.HighlightService;
import ru.exlmoto.code.highlight.enumeration.Mode;
import ru.exlmoto.code.service.DatabaseService;
import ru.exlmoto.code.helper.FilterHelper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.Optional;

@Controller
public class CodeController {
	private final Logger log = LoggerFactory.getLogger(CodeController.class);

	private final FilterHelper filter;
	private final DatabaseService databaseService;
	private final HighlightService highlightService;

	public CodeController(FilterHelper filter,
	                      DatabaseService databaseService,
	                      HighlightService highlightService) {
		this.filter = filter;
		this.databaseService = databaseService;
		this.highlightService = highlightService;
	}

	@RequestMapping(path = { "/", "/{id}" })
	public String index(@PathVariable(name = "id", required = false) Optional<String> id,
	                    @RequestParam(name = "info", required = false) Optional<String> info,
	                    @CookieValue(value = "lang", defaultValue = "ru") String tag,
	                    @CookieValue(value = "options", defaultValue = "auto") String options,
	                    @CookieValue(value = "highlight", defaultValue = "Highlight.js") String highlight,
	                    Model model, CodeForm form) {
		readCookies(form, options, highlight);
		id.flatMap(sId -> filter.getLong(sId).flatMap(databaseService::getCodeSnippet)).ifPresent((snippet) -> {
				Optional.of(snippet.getTitle()).ifPresent(form::setTitle);
				Optional.of(snippet.getOptions()).ifPresent(form::setOptions);
				form.setHighlight(Mode.getMode(snippet.getHighlight()));
				form.setCode(snippet.getCodeRaw());
				model.addAttribute("code", snippet.getCodeHtml());
				model.addAttribute("highlight", Mode.getMode(snippet.getHighlight()));
		});
		model.addAttribute("form", form);

		return "index";
	}

	@PostMapping(path = "/edit")
	public String edit(@Valid CodeForm form, BindingResult bindingResult, HttpServletResponse response) {
		return (bindingResult.hasErrors()) ? "redirect:/?info=empty" : databaseService.saveCodeSnippet(
			filter.getCurrentUnixTime(),
			form.getTitle(),
			form.getOptions(),
			Mode.getName(form.getHighlight()),
			form.getCode(),
			highlightService.renderHtmlFromCode(form.getHighlight(), form.getOptions(), form.getCode())).map((id) -> {
			response.addCookie(new Cookie("options", form.getOptions()));
			response.addCookie(new Cookie("highlight", Mode.getName(form.getHighlight())));
			return String.format("redirect:/%d", id);
		}).orElse("redirect:/");
	}

	private void readCookies(CodeForm form, String options, String highlight) {
		form.setOptions(options);
		form.setHighlight(Mode.getMode(highlight));
	}
}
