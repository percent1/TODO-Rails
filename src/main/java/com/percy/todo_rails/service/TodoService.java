package com.percy.todo_rails.service;

import com.percy.todo_rails.model.Todo;
import com.percy.todo_rails.repository.TodoRepository;
import org.springframework.stereotype.Service;
import com.percy.todo_rails.exception.TodoNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
    }

    public Page<Todo> getTodosPaginated(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo updatedTodo) {
        Todo existingTodo = getTodoById(id);

        existingTodo.setTitle(updatedTodo.getTitle());
        existingTodo.setCompleted(updatedTodo.isCompleted());

        return todoRepository.save(existingTodo);
    }

    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }

        todoRepository.deleteById(id);
    }

    public List<Todo> searchTodos(String title) {
        return todoRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Todo> getTodosByCompletionStatus(boolean completed) {
        return todoRepository.findByCompleted(completed);
    }

    public List<Todo> getTodosSortedAscending() {
        return todoRepository.findAllByOrderByTitleAsc();
    }

    public List<Todo> getTodosSortedDescending() {
        return todoRepository.findAllByOrderByTitleDesc();
    }

    public Page<Todo> queryTodos(String title, boolean completed, Pageable pageable) {
        return todoRepository.findByTitleContainingIgnoreCaseAndCompleted(
            title,
            completed,
            pageable
        );
    }

    public Map<String, Long> getTodoStats() {
        long completed = todoRepository.countByCompletedTrue();
        long incomplete = todoRepository.countByCompletedFalse();

        Map<String, Long> stats = new HashMap<>();

        stats.put("total", completed + incomplete);
        stats.put("completed", completed);
        stats.put("incomplete", incomplete);

        return stats;
    }
}