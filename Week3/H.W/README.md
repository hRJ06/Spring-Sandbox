# COLLEGE MANAGEMENT SYSTEM

<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-2.7.5-brightgreen" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Java-17-blue" alt="Java">
  <img src="https://img.shields.io/badge/Build-Passing-brightgreen" alt="Build Status">
  <img src="https://img.shields.io/badge/License-MIT-yellowgreen" alt="License">
</p>

This repository contains a Spring Boot project that demonstrates the implementation of a **College Management System**. The system provides RESTful APIs for managing entities such as `Student`, `Professor`, `Subject`, and `AdmissionRecord`, along with their relationships.

## ANNOTATIONS USED

- `@RestController` / `@RequestMapping`
- `@GetMapping` / `@PostMapping` / `@PutMapping` / `@PatchMapping` / `@DeleteMapping`
- `@PathVariable` / `@RequestBody`
- `@Autowired`
- `@Service` / `@Repository`
- `@Entity` / `@Table` / `@Id` / `@GeneratedValue` / `@Column`
- `@ExceptionHandler` / `@ResponseStatus`
- `@RestControllerAdvice`
- `@Configuration`

## ENTITIES

### 1. **STUDENT ENTITY**
- `id`
- `name`
- RELATIONS:
  - **ONE-TO-ONE** with `AdmissionRecord` (Owning side: `AdmissionRecordEntity`)
  - **MANY-TO-MANY** with `Professor` (Owning side: `ProfessorEntity`)
  - **MANY-TO-MANY** with `Subject` (Owning side: `SubjectEntity`)

### 2. **PROFESSOR ENTITY**
- `id`
- `title`
- RELATIONS:
  - **MANY-TO-MANY** with `Student` (Owning side: `ProfessorEntity`)
  - **ONE-TO-MANY** with `Subject` (Owning side: `SubjectEntity`)

### 3. **SUBJECT ENTITY**
- `id`
- `title`
- RELATIONS:
  - **MANY-TO-ONE** with `Professor` (Owning side: `SubjectEntity`)
  - **MANY-TO-MANY** with `Student` (Owning side: `SubjectEntity`)

### 4. **ADMISSION RECORD ENTITY**
- `id`
- `fees`
- RELATIONS:
  - **ONE-TO-ONE** with `Student` (Owning side: `AdmissionRecordEntity`)

## REST APIs

### STUDENT CONTROLLER
- **GET** `/students`: Retrieve all students.
- **POST** `/students`: Create a new student.
- **GET** `/students/{studentId}`: Retrieve a student by ID.
- **PUT** `/students/{studentId}`: Update a student's information.
- **DELETE** `/students/{studentId}`: Delete a student by ID.
- **GET** `/students/assignedProfessor/{studentId}`: Retrieve professors assigned to a student.
- **GET** `/students/enrolledSubjects/{studentId}`: Retrieve subjects enrolled by a student.

### PROFESSOR CONTROLLER
- **GET** `/professor`: Retrieve all professors.
- **POST** `/professor`: Create a new professor.
- **GET** `/professor/{professorId}`: Retrieve a professor by ID.
- **PUT** `/professor/{professorId}`: Update a professor's information.
- **DELETE** `/professor/{professorId}`: Delete a professor by ID.
- **GET** `/professor/assignedStudents/{professorId}`: Retrieve students assigned to a professor.
- **GET** `/professor/assignedSubjects/{professorId}`: Retrieve subjects assigned to a professor.
- **PUT** `/professor/{professorId}/assignStudent/{studentId}`: Assign a student to a professor.

### ADMISSION RECORD CONTROLLER
- **POST** `/admission-record/enrollStudent/{studentId}`: Enroll a student in the admission record.
- **GET** `/admission-record`: Retrieve all enrolled students.
- **GET** `/admission-record/{enrollmentId}`: Retrieve a specific student's admission record by ID.
- **DELETE** `/admission-record/{enrollmentId}`: Delete a student's enrollment record.

### SUBJECT CONTROLLER
- **GET** `/subject`: Retrieve all subjects.
- **POST** `/subject`: Create a new subject.
- **GET** `/subject/{subjectId}`: Retrieve a subject by ID.
- **PUT** `/subject/{subjectId}`: Update a subject by ID.
- **DELETE** `/subject/{subjectId}`: Delete a subject by ID.
- **GET** `/subject/assignedProfessor/{subjectId}`: Retrieve the professor assigned to a subject.
- **GET** `/subject/enrolledStudents/{subjectId}`: Retrieve students enrolled in a subject.
- **PUT** `/subject/{subjectId}/enrollStudent/{studentId}`: Enroll a student in a subject.
- **PUT** `/subject/{subjectId}/assignProfessor/{professorId}`: Assign a professor to a subject.


### UML DIAGRAM

<p align="center">
  <img src="https://github.com/user-attachments/assets/08c71ca0-9772-4612-9d24-f2162703869a" alt="Department UML Diagram">
</p>
