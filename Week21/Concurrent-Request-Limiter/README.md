# Concurrent Request Limiter

A Spring Boot application that demonstrate limiting concurrent HTTP requests using Java's `Semaphore`.

<img width="1222" height="485" alt="CONCURRENT-REQUEST-LIMITER" src="https://github.com/user-attachments/assets/c9aae045-fe5b-4e2b-b32d-eca07e2896e4" />

## How It Works?
- A `Semaphore` controls access to a limited number of permits.
- Incoming requests try to acquire a permit.
- If successful, the request is processed.
- If not, the server returns `429 Too Many Requests`.
