package ru.exlmoto.code.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.exlmoto.code.configuration.CodeConfiguration;
import ru.exlmoto.code.controller.enumeration.Lang;
import ru.exlmoto.code.controller.enumeration.Skin;
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
//	private final HighlightService highlight;
	private final CodeConfiguration config;

	public CodeController(UtilityHelper util, CookieHelper cookies, DatabaseService database,
	                      /*HighlightService highlight,*/ CodeConfiguration config) {
		this.util = util;
		this.cookies = cookies;
		this.database = database;
//		this.highlight = highlight;
		this.config = config;
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
		model.addAttribute("snippets",
			util.generateSnippetLinks(database.getCodeSnippets(config.getSnippetCount()), cookies.getLang(request)));
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
			//snippet.setCodeHtml(highlight.highlightCode(form.getHighlight(), filteredOptions, form.getCode()));
			snippet.setCodeHtml(form.getCode());
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

	@RequestMapping(path = "/opts")
	public String skin(@RequestParam(name = "skin", required = false) Optional<String> theme,
	                   @RequestParam(name = "lang", required = false) Optional<String> lang,
	                   HttpServletRequest request, HttpServletResponse response) {
		theme.ifPresent((skin) -> cookies.setSkin(response, Skin.checkSkin(skin)));
		lang.ifPresent((language) -> cookies.setLang(response, Lang.checkLang(language)));

		// TODO: check this in the real world.
		return "redirect:" + Optional.ofNullable(request.getHeader(HttpHeaders.HOST)).map((host) ->
			Optional.ofNullable(request.getHeader(HttpHeaders.REFERER)).map((referer) ->
				referer.substring(referer.lastIndexOf(host) + host.length()))).map(Optional::get).orElse("/");
	}

	private void readCookies(Model model, CodeForm form, HttpServletRequest request) {
		model.addAttribute(CookieHelper.SKIN, cookies.getSkin(request));
		model.addAttribute(CookieHelper.LANG, cookies.getLang(request));
		form.setOptions(cookies.getOptions(request));
		form.setHighlight(cookies.getHighlight(request));
	}
}
