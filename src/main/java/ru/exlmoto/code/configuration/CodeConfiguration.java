package ru.exlmoto.code.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import ru.exlmoto.code.controller.enumeration.Lang;

import java.util.Locale;

@ConfigurationProperties(prefix = "code")
public class CodeConfiguration {
	private String dateFormat;
	private Integer snippetCount;

	@Bean("cookieLocaleResolver")
	public LocaleResolver localeResolver() {
		final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(Locale.forLanguageTag(Lang.ru.name()));
		cookieLocaleResolver.setCookieName("lang");
		return cookieLocaleResolver;
	}

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
