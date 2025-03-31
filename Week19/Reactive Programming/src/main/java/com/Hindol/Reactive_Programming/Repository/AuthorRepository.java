package com.Hindol.Reactive_Programming.Repository;

import com.Hindol.Reactive_Programming.Entity.Author;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends ReactiveCrudRepository<Author, Long> {
}
