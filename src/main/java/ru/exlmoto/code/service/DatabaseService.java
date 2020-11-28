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

	public Optional<Long> saveCodeSnippet(CodeEntity snippet) {
		try {
			final CodeEntity savedSnippet = codeRepository.saveAndFlush(snippet);
			return Optional.of(savedSnippet.getId());
		} catch (DataAccessException dae) {
			log.error(String.format("Cannot save Code Snippet (timestamp: '%d') to database: '%s'.",
				snippet.getTimestamp(), dae.getLocalizedMessage()), dae);
		}
		return Optional.empty();
	}

	public Optional<CodeEntity> getCodeSnippet(long id) {
		try {
			return codeRepository.findById(id);
		} catch (DataAccessException dae) {
			log.error(String.format("Cannot get Code Snippet (id: '%d') from database: '%s'.",
				id, dae.getLocalizedMessage()), dae);
		}
		return Optional.empty();
	}

	public boolean deleteCodeSnippet(long id) {
		try {
			codeRepository.deleteById(id);
			return true;
		} catch (DataAccessException dae) {
			log.error(String.format("Cannot delete Code Snippet (id: '%d') from database: '%s'.",
				id, dae.getLocalizedMessage()), dae);
		}
		return false;
	}
}
