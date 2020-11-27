package ru.exlmoto.code.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Component
public class ResourceHelper {
	private final Logger log = LoggerFactory.getLogger(ResourceHelper.class);

	public String readFileToString(String path) {
		return readFileToString(path, StandardCharsets.UTF_8);
	}

	public String readFileToString(String path, Charset charset) {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource(path);
		try (Reader reader = new InputStreamReader(resource.getInputStream(), charset)) {
			return FileCopyUtils.copyToString(reader);
		} catch (IOException ioe) {
			log.error(String.format("Cannot find resource on path '%s', error: '%s'.",
				resource.getFilename(), ioe.getLocalizedMessage()), ioe);
			throw new UncheckedIOException(ioe);
		}
	}
}
