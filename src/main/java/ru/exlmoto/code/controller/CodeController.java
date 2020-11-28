package ru.exlmoto.code.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.exlmoto.code.entity.CodeEntity;
import ru.exlmoto.code.form.CodeForm;
import ru.exlmoto.code.highlight.HighlightService;
import ru.exlmoto.code.highlight.enumeration.Mode;
import ru.exlmoto.code.helper.CookieHelper;
import ru.exlmoto.code.service.DatabaseService;
import ru.exlmoto.code.helper.UtilityHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.Valid;

import java.util.Optional;

@Controller
public class CodeController {
	private final UtilityHelper util;
	private final CookieHelper cookies;
	private final DatabaseService database;
	private final HighlightService highlight;

	public CodeController(UtilityHelper util, CookieHelper cookies,
	                      DatabaseService database, HighlightService highlight) {
		this.util = util;
		this.cookies = cookies;
		this.database = database;
		this.highlight = highlight;
	}

	@RequestMapping(path = { "/", "/{id}" })
	public String index(@PathVariable(name = "id", required = false) Optional<String> id,
	                    @RequestParam(name = "info", required = false) Optional<String> info,
	                    HttpServletRequest request, Model model, CodeForm form) {
		readCookies(model, form, request);
		id.flatMap(sId -> util.getLong(sId).flatMap(database::getCodeSnippet)).ifPresent((snippet) -> {
				Optional.ofNullable(snippet.getTitle()).ifPresent(form::setTitle);
				Optional.ofNullable(snippet.getOptions()).ifPresent(form::setOptions);
				form.setHighlight(Mode.getMode(snippet.getHighlight()));
				form.setCode(snippet.getCodeRaw());
				form.setRenderTime(snippet.getRenderTime());
				model.addAttribute("code", snippet.getCodeHtml());
				model.addAttribute("highlight", Mode.getMode(snippet.getHighlight()));
		});
		model.addAttribute("form", form);

		return "index";
	}

	@PostMapping(path = "/edit")
	public String edit(@Valid CodeForm form, BindingResult bindingResult, HttpServletResponse response) {
		if (!bindingResult.hasErrors()) {
			final String filteredOptions = util.getCorrectOptions(form.getOptions());

			final CodeEntity snippet = new CodeEntity();
			snippet.setTimestamp(util.getCurrentUnixTime());
			snippet.setTitle(form.getTitle());
			snippet.setOptions(filteredOptions);
			snippet.setHighlight(form.getHighlight().name());
			snippet.setCodeRaw(form.getCode());
			final long start = System.currentTimeMillis();
			snippet.setCodeHtml(highlight.highlightCode(form.getHighlight(), filteredOptions, form.getCode()));
			snippet.setRenderTime(System.currentTimeMillis() - start);

			return database.saveCodeSnippet(snippet).map((id) -> {
				cookies.setOptions(response, filteredOptions);
				cookies.setHighlight(response, form.getHighlight());
				return String.format("redirect:/%d", id);
			}).orElse("redirect:/?info=database");
		}
		return "redirect:/?info=empty";
	}

	@RequestMapping(path = "/delete/{id}")
	public String delete(@PathVariable(name = "id", required = false) Optional<String> id) {
		Optional<Long> deleteId = id.flatMap(util::getLong);
		if (deleteId.isPresent() && database.deleteCodeSnippet(deleteId.get()))
			return "redirect:/?info=deleteOk";
		return "redirect:/?info=deleteError";
	}

	private void readCookies(Model model, CodeForm form, HttpServletRequest request) {
		model.addAttribute(CookieHelper.SKIN, cookies.getSkin(request));
		form.setOptions(cookies.getOptions(request));
		form.setHighlight(cookies.getHighlight(request));
	}
}
