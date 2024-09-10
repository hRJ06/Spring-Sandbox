package com.Hindol.Week3HW.Repository;

import com.Hindol.Week3HW.Entity.AuthorEntity;
import com.Hindol.Week3HW.Entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByPublishDateAfter(LocalDate date);
    List<BookEntity> findByTitleLike(String title);
}
