package ru.exlmoto.code.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ru.exlmoto.code.entity.CodeEntity;

import java.util.List;

public interface CodeRepository extends JpaRepository<CodeEntity, Long> {
	List<CodeEntity> findAllByOrderByIdDesc(Pageable pageable);
}
