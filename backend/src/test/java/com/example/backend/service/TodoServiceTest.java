package com.example.backend.service;

import com.example.backend.entity.Todo;
import com.example.backend.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private Todo todo;

    @BeforeEach
    void setUp() {
        todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Write unit tests");
        todo.setStatus("OPEN");
        todo.setPriority("HIGH");
        todo.setDueDate("2026-04-20");
    }

    @Test
    void getAllTodos_shouldReturnTodoList() {
        when(todoRepository.findAll()).thenReturn(List.of(todo));

        List<Todo> result = todoService.getAllTodos();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Write unit tests", result.get(0).getTitle());
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void getTodoById_shouldReturnTodo_whenTodoExists() {
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        Todo result = todoService.getTodoById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Write unit tests", result.getTitle());
        verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    void getTodoById_shouldThrowException_whenTodoDoesNotExist() {
        when(todoRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> todoService.getTodoById(99L)
        );

        assertEquals("Todo not found with id: 99", exception.getMessage());
        verify(todoRepository, times(1)).findById(99L);
    }

    @Test
    void createTodo_shouldSaveAndReturnTodo() {
        Todo newTodo = new Todo();
        newTodo.setTitle("Create service test");
        newTodo.setStatus("OPEN");
        newTodo.setPriority("MEDIUM");
        newTodo.setDueDate("2026-04-22");

        Todo savedTodo = new Todo();
        savedTodo.setId(2L);
        savedTodo.setTitle("Create service test");
        savedTodo.setStatus("OPEN");
        savedTodo.setPriority("MEDIUM");
        savedTodo.setDueDate("2026-04-22");

        when(todoRepository.save(any(Todo.class))).thenReturn(savedTodo);

        Todo result = todoService.createTodo(newTodo);

        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Create service test", result.getTitle());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void updateTodo_shouldUpdateAndReturnTodo_whenTodoExists() {
        Todo updatedTodo = new Todo();
        updatedTodo.setTitle("Updated title");
        updatedTodo.setStatus("IN_PROGRESS");
        updatedTodo.setPriority("LOW");
        updatedTodo.setDueDate("2026-04-25");

        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Todo result = todoService.updateTodo(1L, updatedTodo);

        assertNotNull(result);
        assertEquals("Updated title", result.getTitle());
        assertEquals("IN_PROGRESS", result.getStatus());
        assertEquals("LOW", result.getPriority());
        assertEquals("2026-04-25", result.getDueDate());

        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void updateTodo_shouldThrowException_whenTodoDoesNotExist() {
        Todo updatedTodo = new Todo();
        updatedTodo.setTitle("Updated title");
        updatedTodo.setStatus("DONE");
        updatedTodo.setPriority("HIGH");
        updatedTodo.setDueDate("2026-04-30");

        when(todoRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> todoService.updateTodo(99L, updatedTodo)
        );

        assertEquals("Todo not found with id: 99", exception.getMessage());
        verify(todoRepository, times(1)).findById(99L);
        verify(todoRepository, never()).save(any(Todo.class));
    }

    @Test
    void deleteTodo_shouldDeleteTodo_whenTodoExists() {
        when(todoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(todoRepository).deleteById(1L);

        assertDoesNotThrow(() -> todoService.deleteTodo(1L));

        verify(todoRepository, times(1)).existsById(1L);
        verify(todoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTodo_shouldThrowException_whenTodoDoesNotExist() {
        when(todoRepository.existsById(99L)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> todoService.deleteTodo(99L)
        );

        assertEquals("Todo not found with id: 99", exception.getMessage());
        verify(todoRepository, times(1)).existsById(99L);
        verify(todoRepository, never()).deleteById(anyLong());
    }
}
