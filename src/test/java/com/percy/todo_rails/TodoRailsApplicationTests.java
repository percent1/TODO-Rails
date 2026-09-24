package com.percy.todo_rails;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import com.percy.todo_rails.model.Todo;
import com.percy.todo_rails.repository.TodoRepository;

@SpringBootTest
@AutoConfigureMockMvc
class TodoRailsApplicationTests {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MockMvc mockMvc;

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

    @Test
    void shouldGetAllTodos() throws Exception {
        todoRepository.save(new Todo("Test GET endpoint"));

        mockMvc.perform(get("/api/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test GET endpoint")));
    }

	@Test
void shouldCreateTodo() throws Exception {
    mockMvc.perform(post("/api/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"Learn Spring Boot testing\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Learn Spring Boot testing"))
            .andExpect(jsonPath("$.completed").value(false));
}

@Test
void shouldRejectBlankTodoTitle() throws Exception {
    mockMvc.perform(post("/api/todos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title").value("Title cannot be blank"));
}

@Test
void shouldUpdateTodo() throws Exception {
    Todo todo = todoRepository.save(new Todo("Original title"));

    mockMvc.perform(put("/api/todos/" + todo.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"Updated title\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated title"))
            .andExpect(jsonPath("$.completed").value(false));
}

@Test
void shouldRejectBlankTitleWhenUpdatingTodo() throws Exception {
    Todo todo = todoRepository.save(new Todo("Original title"));

    mockMvc.perform(put("/api/todos/" + todo.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"title\":\"\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.title").value("Title cannot be blank"));
}

@Test
void shouldReturn404WhenTodoDoesNotExist() throws Exception {
    mockMvc.perform(get("/api/todos/999999"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Todo not found with id: 999999"));
}

@Test
void shouldDeleteTodo() throws Exception {
    Todo todo = todoRepository.save(new Todo("Todo to delete"));

    mockMvc.perform(delete("/api/todos/" + todo.getId()))
            .andExpect(status().isNoContent());

    assertFalse(todoRepository.existsById(todo.getId()));
}

@Test
void shouldReturn404WhenDeletingTodoDoesNotExist() throws Exception {
    mockMvc.perform(delete("/api/todos/999999"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Todo not found with id: 999999"));
}

}