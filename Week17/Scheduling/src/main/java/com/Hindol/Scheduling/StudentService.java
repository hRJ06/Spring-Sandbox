package com.Hindol.Scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final StudentInfoService studentInfoService;
    public Student getStudentInfo() {
        try {
            long start = System.currentTimeMillis();
            log.info("Starting now - {}", Thread.currentThread().getName());
            /* Running in parallel */
            CompletableFuture<String> name = studentInfoService.getName();
            CompletableFuture<String> college = studentInfoService.getCollege();
            CompletableFuture<String> id = studentInfoService.getId();

            CompletableFuture.allOf(name, college, id).join();
            Student student = new Student(
                    name.get(),
                    college.get(),
                    id.get()
            );
            long end = System.currentTimeMillis();
            log.info("Ended in {}", end - start);
            return student;
        }
        catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
