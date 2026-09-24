package com.percy.todo_rails.controller;

import com.percy.todo_rails.model.Todo;
import com.percy.todo_rails.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @GetMapping("/search")
    public List<Todo> searchTodos(@RequestParam String title) {
        return todoService.searchTodos(title);
    }

    @GetMapping("/completed")
    public List<Todo> getTodosByCompletionStatus(@RequestParam boolean completed) {
        return todoService.getTodosByCompletionStatus(completed);
    }

    @GetMapping("/sort/asc")
    public List<Todo> getTodosSortedAscending() {
        return todoService.getTodosSortedAscending();
    }

    @GetMapping("/sort/desc")
    public List<Todo> getTodosSortedDescending() {
        return todoService.getTodosSortedDescending();
    }

    @GetMapping("/page")
    public Page<Todo> getTodosPaginated(Pageable pageable) {
        return todoService.getTodosPaginated(pageable);
    }

    @GetMapping("/query")
    public Page<Todo> queryTodos(@RequestParam String title, @RequestParam boolean completed,Pageable pageable) {
        return todoService.queryTodos(title, completed, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        return ResponseEntity.ok(todoService.getTodoById(id));
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
        Todo createdTodo = todoService.createTodo(todo);

        return ResponseEntity
            .status(201)
            .body(createdTodo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @Valid @RequestBody Todo todo) {
        return ResponseEntity.ok(todoService.updateTodo(id, todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

  
}