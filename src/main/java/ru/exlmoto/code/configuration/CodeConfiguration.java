package ru.exlmoto.code.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "code")
public class CodeConfiguration {
	private String dateFormat;
	private String dateFormatFull;
	private Integer snippetCount;
	private Integer snippetMaxLength;
	private String adminUsername;
	private String adminPassword;

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getDateFormatFull() {
		return dateFormatFull;
	}

	public void setDateFormatFull(String dateFormatFull) {
		this.dateFormatFull = dateFormatFull;
	}

	public Integer getSnippetCount() {
		return snippetCount;
	}

	public void setSnippetCount(Integer snippetCount) {
		this.snippetCount = snippetCount;
	}

	public Integer getSnippetMaxLength() {
		return snippetMaxLength;
	}

	public void setSnippetMaxLength(Integer snippetMaxLength) {
		this.snippetMaxLength = snippetMaxLength;
	}

	public String getAdminUsername() {
		return adminUsername;
	}

	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
}
