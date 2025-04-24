package com.Hindol.BookService.Repository;

import com.Hindol.BookService.Entity.Author;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends ReactiveCrudRepository<Author, Long> {
}
