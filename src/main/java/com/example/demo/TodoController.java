package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TodoController {
    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/todos")
    public Iterable<Todo> getTodos() {
        return todoRepository.findAll();
    }

    @PostMapping("/todos")
    public Todo createTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    @GetMapping("/todos/{id}")
    public Todo getTodo(@PathVariable Integer id) {
        Optional<Todo> todo = todoRepository.findById(id);
        return todo.orElse(null);
    }

    @PutMapping("/todos/{id}")
    public Todo updateTodo(@PathVariable Integer id, @RequestBody Todo data) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isPresent()) {
            data.setId(todo.get().getId());
            if (data.getContent()==null) data.setContent(todo.get().getContent());
            if (data.getCompleted()==null) data.setCompleted(todo.get().getCompleted());
            return todoRepository.save(data);
        }
        return null;
    }

    @DeleteMapping("/todos/{id}")
    public void deleteTodo(@PathVariable Integer id) {
        Optional<Todo> todo = todoRepository.findById(id);
        todo.ifPresent(todoRepository::delete); // Named Functional callback
    }
}
