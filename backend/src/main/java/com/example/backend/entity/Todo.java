package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be at most 100 characters")
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @NotBlank(message = "Status is required")
    @Pattern(
            regexp = "OPEN|IN_PROGRESS|DONE",
            message = "Status must be OPEN, IN_PROGRESS, or DONE"
    )
    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @NotBlank(message = "Priority is required")
    @Pattern(
            regexp = "LOW|MEDIUM|HIGH",
            message = "Priority must be LOW, MEDIUM or HIGH"
    )
    @Column(name = "priority", length = 10, nullable = false)
    private String priority;

    @NotBlank(message = "Due date is required")
    @Pattern(
            regexp = "\\d{4}-\\d{2}-\\d{2}",
            message = "Due date must be in yyyy-MM-dd format"
    )
    @Column(name = "due_date", nullable = false)
    private String dueDate;

    public Todo() {
    }

    public Todo(String title, String status, String priority, String dueDate) {
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public Todo(Long id, String title, String status, String priority, String dueDate) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
