# Library Management System

<p align="center">
  <img src="https://img.shields.io/badge/build-passing-brightgreen" alt="Build Status">
  <img src="https://img.shields.io/badge/license-MIT-blue" alt="License">
  <img src="https://img.shields.io/badge/java-17-red" alt="Java">
</p>

This repository contains a Spring Boot project that demonstrates the implementation of a Library Management System. The system provides RESTful APIs for managing entities such as Author and Book, along with their relationships.

## Annotations Used

- `@RestController` / `@RequestMapping`
- `@GetMapping` / `@PostMapping` / `@PutMapping` / `@PatchMapping` / `@DeleteMapping`
- `@PathVariable` / `@RequestBody`
- `@Autowired`
- `@Service` / `@Repository`
- `@Entity` / `@Table` / `@Id` / `@GeneratedValue` / `@Column`
- `@ExceptionHandler` / `@ResponseStatus`
- `@RestControllerAdvice`
- `@Configuration`
- `@MappedSuperclass`
- `@Audited`

## Entities

1. **AUTHOR ENTITY**
   - `id`
   - `name`
   - **RELATIONS:**
     - One-to-Many with `Book` (Owning side: `BookEntity`)

2. **BOOK ENTITY**
   - `id`
   - `title`
   - `description`
   - `publishDate`
   - **RELATIONS:**
     - Many-to-One with `Author` (Owning side: `BookEntity`)

## REST APIs

### Author Controller

- **GET** `/author`: Retrieve all authors. Optionally filter by name.
- **GET** `/author/{authorId}`: Retrieve an author by ID.
- **POST** `/author`: Create a new author.
- **PUT** `/author/{authorId}`: Update an author by ID.
- **DELETE** `/author/{authorId}`: Delete an author by ID.

### Book Controller

- **GET** `/book`: Retrieve all books.
- **GET** `/book/{bookId}`: Retrieve a book by ID.
- **POST** `/book`: Create a new book.
- **PUT** `/book/{bookId}`: Update a book by ID.
- **DELETE** `/book/{bookId}`: Delete a book by ID.
- **GET** `/book/getAfterDate/{date}`: Retrieve books published after a specific date.
- **GET** `/book/title/{title}`: Retrieve books by title.
- **GET** `/book/author/{authorId}`: Retrieve books by a specific author.
- **PUT** `/book/{bookId}/author/{authorId}`: Assign an author to a book.

## UML Diagram

<p align="center">
  <img src="https://github.com/user-attachments/assets/8851086b-61bb-48b2-b90a-2f14f216130c" alt="UML Diagram">
</p>
