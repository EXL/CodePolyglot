package ru.exlmoto.code.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import ru.exlmoto.code.entity.CodeEntity;
import ru.exlmoto.code.repository.CodeRepository;

import java.util.Optional;

@Service
public class DatabaseService {
	private final Logger log = LoggerFactory.getLogger(DatabaseService.class);

	private final CodeRepository codeRepository;

	public DatabaseService(CodeRepository codeRepository) {
		this.codeRepository = codeRepository;
	}

	public Optional<Long> saveCodeSnippet(Long timestamp,
	                                      String title,
	                                      String options,
	                                      String highlight,
	                                      String codeRaw,
	                                      String codeHtml) {
		try {
			final CodeEntity snippet = new CodeEntity(timestamp, title, options, highlight, codeRaw, codeHtml);
			final CodeEntity savedSnippet = codeRepository.saveAndFlush(snippet);
			return Optional.of(savedSnippet.getId());
		} catch (DataAccessException dae) {
			log.error(String.format("Cannot save Code Snippet (timestamp: '%d') to DataBase: '%s'.",
				timestamp, dae.getLocalizedMessage()), dae);
			return Optional.empty();
		}
	}
}
