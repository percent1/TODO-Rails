package com.percy.todo_rails.repository;

import com.percy.todo_rails.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByTitleContainingIgnoreCase(String title);
    List<Todo> findByCompleted(boolean completed);
    List<Todo> findAllByOrderByTitleAsc();
    List<Todo> findAllByOrderByTitleDesc();
    Page<Todo> findByTitleContainingIgnoreCaseAndCompleted(String title, boolean completed, Pageable pageable);
    long countByCompletedTrue();
    long countByCompletedFalse();
}