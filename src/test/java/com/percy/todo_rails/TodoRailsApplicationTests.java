package com.percy.todo_rails;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
}