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
