package com.scheduler.scheduler.infrastructure.repository;

import com.scheduler.scheduler.domain.ToDo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TodoRepository extends JpaRepository<Todo, UUID> {
}
