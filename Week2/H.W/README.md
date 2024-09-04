# Department Management API

<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-2.7.5-brightgreen" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Java-17-blue" alt="Java">
  <img src="https://img.shields.io/badge/Build-Passing-brightgreen" alt="Build Status">
  <img src="https://img.shields.io/badge/License-MIT-yellowgreen" alt="License">
</p>

This repository contains a Spring Boot project that demonstrates the use of various annotations in the Spring Web Framework. It includes REST endpoints for managing a `Department` entity, custom annotations for input validation, and a global response handler.

## Annotations Used

- `@RestController` / `@RequestMapping`
- `@GetMapping` / `@PostMapping` / `@PutMapping` / `@PatchMapping` / `@DeleteMapping`
- `@PathVariable` / `@RequestBody`
- `@Autowired`
- `@Service` / `@Repository`
- `@Entity` / `@Table` / `@Id` / `@GeneratedValue` / `@Column`
- `@ExceptionHandler` / `@ResponseStatus`
- `@RestControllerAdvice` (for global response handling)
- `@Configuration`

## Department Entity

The `Department` entity includes the following fields:
- `id`
- `departmentNo`
- `title`
- `isActive`
- `createdAt`
- `password`

### REST APIs

- **GET** `/department`: Retrieve all departments.
- **POST** `/department`: Create a new department.
- **PUT** `/department/{id}`: Update an existing department.
- **PATCH** `/department/{id}`: Partially update a department.
- **DELETE** `/department/{id}`: Delete a department.
- **GET** `/departments/{id}`: Retrieve a department by its ID.

### UML Diagram

<p align="center">
  <img src="https://github.com/user-attachments/assets/a64d629a-fe0c-4931-bf76-408bf44578ad" alt="Department UML Diagram">
</p>

## Homework

1. Write Exception Handling logic for the `Department` entity.
2. Create a custom annotation to handle prime number inputs.
3. Create a custom annotation to check if a Password String field satisfies the following criteria:
   - Contains one uppercase letter
   - Contains one lowercase letter
   - Contains one special character
   - Minimum length is 10 characters

---
