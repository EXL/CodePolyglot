package ru.exlmoto.code.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "code")
public class CodeConfiguration {
	private String dateFormat;
	private Integer snippetCount;

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Integer getSnippetCount() {
		return snippetCount;
	}

	public void setSnippetCount(Integer snippetCount) {
		this.snippetCount = snippetCount;
	}
}
