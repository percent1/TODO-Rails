package com.percy.todo_rails.repository;

import com.percy.todo_rails.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByTitleContainingIgnoreCase(String title);
    List<Todo> findByCompleted(boolean completed);
    List<Todo> findAllByOrderByTitleAsc();
    List<Todo> findAllByOrderByTitleDesc();
}