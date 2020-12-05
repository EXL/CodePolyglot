package ru.exlmoto.code.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ru.exlmoto.code.entity.CodeEntity;
import ru.exlmoto.code.helper.UtilityHelper;
import ru.exlmoto.code.service.DatabaseService;

@RestController
public class ApiController {
	private final DatabaseService database;
	private final UtilityHelper util;

	public ApiController(DatabaseService database, UtilityHelper util) {
		this.database = database;
		this.util = util;
	}

	@GetMapping(value = "/api/raw/{id}", produces = "text/plain;charset=UTF-8")
	public String raw(@PathVariable(name = "id") String id) {
		return util.getLong(id).flatMap(database::getCodeSnippet).map(CodeEntity::getCodeRaw).orElse(
			"Error: Cannot get code snippet with '" + id + "' id."
		);
	}
}
