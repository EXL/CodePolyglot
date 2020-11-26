package ru.exlmoto.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.exlmoto.code.entity.CodeEntity;

public interface CodeRepository extends JpaRepository<CodeEntity, Long> {

}
