package com.percy.todo_rails.repository;

import com.percy.todo_rails.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}