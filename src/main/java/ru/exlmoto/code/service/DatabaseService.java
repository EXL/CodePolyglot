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

package ru.exlmoto.code.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ru.exlmoto.code.entity.CodeEntity;
import ru.exlmoto.code.repository.CodeRepository;

import java.util.ArrayList;
import java.util.List;
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

	public List<CodeEntity> getCodeSnippets(int count) {
		try {
			return codeRepository.findAllByOrderByIdDesc(PageRequest.of(0, count));
		} catch (DataAccessException dae) {
			log.error(String.format("Cannot get page with Code Snippets (count: '%d') from database: '%s'.",
				count, dae.getLocalizedMessage()), dae);
		}
		return new ArrayList<>();
	}
}
