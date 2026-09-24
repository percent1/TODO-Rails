package com.percy.todo_rails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.percy.todo_rails.model.Todo;
import com.percy.todo_rails.repository.TodoRepository;

@SpringBootTest
class TodoRailsApplicationTests {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldSaveTodo() {
        Todo todo = new Todo("Learn automated testing");

        Todo savedTodo = todoRepository.save(todo);

        assertEquals("Learn automated testing", savedTodo.getTitle());
        assertFalse(savedTodo.isCompleted());
    }
}