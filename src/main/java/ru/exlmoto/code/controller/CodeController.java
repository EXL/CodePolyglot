package ru.exlmoto.code.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.exlmoto.code.form.CodeForm;
import ru.exlmoto.code.highlight.Mode;

@Controller
public class CodeController {
	private final Logger log = LoggerFactory.getLogger(CodeController.class);

	@RequestMapping(path = "/")
	public String index(Model model, CodeForm form) {
		form.setHighlight(Mode.HighlightPygments); // TODO: get/set from cookie
		model.addAttribute("form", form);

		return "index";
	}

	@PostMapping(path = "/edit")
	public String edit(CodeForm form) {
		log.info(form.getTitle());
		log.info(form.getOptions());
		log.info(form.getCode());
		log.info(Mode.getName(form.getHighlight()));

		return "redirect:/";
	}
}
