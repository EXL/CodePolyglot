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
