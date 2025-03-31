package com.Hindol.Reactive_Programming.Repository;

import com.Hindol.Reactive_Programming.Entity.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, Long> {
}
