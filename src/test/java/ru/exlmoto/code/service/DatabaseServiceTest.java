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

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import ru.exlmoto.code.entity.CodeEntity;
import ru.exlmoto.code.repository.CodeRepository;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class DatabaseServiceTest {
	@Autowired
	private DatabaseService database;

	@MockBean
	private CodeRepository repository;

	@Test
	public void testSaveCodeSnippet() {
		doThrow(new InvalidDataAccessResourceUsageException("saveCodeSnippet()"))
			.when(repository).saveAndFlush(any());

		assertFalse(database.saveCodeSnippet(new CodeEntity()).isPresent());
	}

	@Test
	public void testGetCodeSnippet() {
		doThrow(new InvalidDataAccessResourceUsageException("getCodeSnippet()"))
			.when(repository).findById(anyLong());

		assertFalse(database.getCodeSnippet(1L).isPresent());
	}

	@Test
	public void testGetCodeSnippets() {
		doThrow(new InvalidDataAccessResourceUsageException("getCodeSnippets()"))
			.when(repository).findAllByOrderByIdDesc(any());

		assertThat(database.getCodeSnippets(50)).isEmpty();
	}

	@Test
	public void testDeleteCodeSnippet() {
		doThrow(new InvalidDataAccessResourceUsageException("deleteCodeSnippet()"))
			.when(repository).deleteById(anyLong());

		assertFalse(database.deleteCodeSnippet(1L));
	}
}
