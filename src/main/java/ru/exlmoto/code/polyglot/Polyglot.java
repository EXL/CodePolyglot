package ru.exlmoto.code.polyglot;

import org.graalvm.polyglot.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.StringUtils;

import java.util.Optional;

public abstract class Polyglot {
	private final Logger log = LoggerFactory.getLogger(Polyglot.class);

	protected final Context polyglot = Context.newBuilder(Language.names()).allowAllAccess(true).allowIO(true).build();

	public Optional<String> execute(String sourceCode) {
		try {
			String output = executeAux(sourceCode);
			if (StringUtils.hasText(output)) {
				return Optional.of(output);
			}
		} catch (RuntimeException re) {
			log.error(String.format("Cannot execute source code: '%s'.", re.getLocalizedMessage()), re);
		}
		return Optional.empty();
	}

	protected abstract String executeAux(String sourceCode);

	public abstract String loadRequiredLibrariesAndGetVersion();
}
