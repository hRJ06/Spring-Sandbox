package com.Hindol.ReactiveProgramming.Repository;

import com.Hindol.ReactiveProgramming.Entity.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, Long> {
}
