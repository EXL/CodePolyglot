package ru.exlmoto.code.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.exlmoto.code.form.CodeForm;
import ru.exlmoto.code.highlight.Mode;
import ru.exlmoto.code.service.DatabaseService;
import ru.exlmoto.code.util.FilterHelper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class CodeController {
	private final Logger log = LoggerFactory.getLogger(CodeController.class);

	private final DatabaseService databaseService;
	private final FilterHelper filterHelper;

	public CodeController(DatabaseService databaseService, FilterHelper filterHelper) {
		this.databaseService = databaseService;
		this.filterHelper = filterHelper;
	}

	@RequestMapping(path = { "/", "/{id}" })
	public String index(@PathVariable(name = "id", required = false) String id,
	                    @RequestParam(name = "error", required = false) String error,
	                    @CookieValue(value = "lang", defaultValue = "ru") String tag,
	                    @CookieValue(value = "options", defaultValue = "auto") String options,
	                    @CookieValue(value = "highlight", defaultValue = "Pygments") String highlight,
	                    Model model, CodeForm form) {
		log.info("id: " + id);
		log.info("error: " + error);

		readCookies(form, options, highlight);
		

		model.addAttribute("form", form);

		return "index";
	}

	@PostMapping(path = "/edit")
	public String edit(@Valid CodeForm form, BindingResult bindingResult, HttpServletResponse response) {
		final String highlight = Mode.getName(form.getHighlight());
		return (bindingResult.hasErrors()) ? "redirect:/?error=empty" : databaseService.saveCodeSnippet(
			filterHelper.getCurrentUnixTime(),
			form.getOptions(),
			form.getTitle(),
			highlight,
			form.getCode(),
			form.getCode()
		).map((id) -> {
			response.addCookie(new Cookie("options", form.getOptions()));
			response.addCookie(new Cookie("highlight", highlight));
			return String.format("redirect:/%d", id);
		}).orElse("redirect:/");
	}

	private void readCookies(CodeForm form, String options, String highlight) {
		form.setOptions(options);
		form.setHighlight(Mode.getMode(highlight));
	}
}
