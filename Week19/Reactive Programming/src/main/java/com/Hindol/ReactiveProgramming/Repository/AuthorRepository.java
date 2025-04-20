package com.Hindol.ReactiveProgramming.Repository;

import com.Hindol.ReactiveProgramming.Entity.Author;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends ReactiveCrudRepository<Author, Long> {
}
