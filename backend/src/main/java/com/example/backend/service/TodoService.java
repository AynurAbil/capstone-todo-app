package com.example.backend.service;

import com.example.backend.entity.Todo;
import com.example.backend.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .orElseThrow(() -> new IllegalArgumentException("Todo not found with id: " + id));
    }

    public Todo createTodo(Todo todo) {
        todo.setId(null);
        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo updatedTodo) {
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found with id: " + id));

        existingTodo.setTitle(updatedTodo.getTitle());
        existingTodo.setStatus(updatedTodo.getStatus());
        existingTodo.setPriority(updatedTodo.getPriority());
        existingTodo.setDueDate(updatedTodo.getDueDate());

        return todoRepository.save(existingTodo);
    }

    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new IllegalArgumentException("Todo not found with id: " + id);
        }

        todoRepository.deleteById(id);
    }
}
