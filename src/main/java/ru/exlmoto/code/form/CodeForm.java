package ru.exlmoto.code.form;

import ru.exlmoto.code.highlight.enumeration.Mode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CodeForm {
	private String date;
	private String title;
	private String options;

	@NotBlank
	private String code;

	@NotNull
	private Mode highlight;

	private Long renderTime;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

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

	public Long getRenderTime() {
		return renderTime;
	}

	public void setRenderTime(Long renderTime) {
		this.renderTime = renderTime;
	}

	@Override
	public String toString() {
		return "CodeForm{" +
			"date='" + date + '\'' +
			", title='" + title + '\'' +
			", options='" + options + '\'' +
			", code='" + "<skipped>" + '\'' +
			", highlight=" + highlight +
			", renderTime=" + renderTime +
			'}';
	}
}
