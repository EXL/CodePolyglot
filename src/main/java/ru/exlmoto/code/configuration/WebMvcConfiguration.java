package ru.exlmoto.code.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import ru.exlmoto.code.controller.enumeration.Lang;
import ru.exlmoto.code.helper.CookieHelper;

import java.util.Locale;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	@Bean
	public LocaleResolver localeResolver() {
		final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(Locale.forLanguageTag(Lang.ru.name()));
		cookieLocaleResolver.setCookieName(CookieHelper.LANG);
		return cookieLocaleResolver;
	}
}
