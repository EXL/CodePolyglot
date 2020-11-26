package ru.exlmoto.code.form;

import ru.exlmoto.code.highlight.enumeration.Mode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CodeForm {
	private String title;
	private String options;

	@NotBlank
	private String code;

	@NotNull
	private Mode highlight;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Mode getHighlight() {
		return highlight;
	}

	public void setHighlight(Mode highlight) {
		this.highlight = highlight;
	}
}
