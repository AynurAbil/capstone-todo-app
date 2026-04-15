# Capstone Todo App

A full-stack Todo application built with **Spring Boot** for the backend and **React + Vite + TypeScript** for the frontend.

## Project Overview

This project allows users to:

- register and log in
- create todos
- view all todos
- delete todos
- access protected routes after authentication

The application is split into two parts:

- **backend/** → Spring Boot REST API with authentication, H2 database, and tests
- **frontend/** → React UI with protected routes, todo forms, todo list, and unit tests

## Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Security
- Maven Wrapper
- H2 Database
- JUnit / Mockito / MockMvc
- JaCoCo coverage

### Frontend
- React
- Vite
- TypeScript
- React Router
- Vitest
- React Testing Library

## Repository Structure

```text
capstone-todo-app/
├── .github/workflows/        # GitHub Actions CI pipeline
├── backend/                  # Spring Boot backend
├── frontend/                 # React frontend
└── README.md                 # Project overview