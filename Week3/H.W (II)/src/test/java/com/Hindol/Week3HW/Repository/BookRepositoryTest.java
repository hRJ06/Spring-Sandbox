package com.Hindol.Week3HW.Repository;

import com.Hindol.Week3HW.Entity.BookEntity;
import com.Hindol.Week3HW.TestContainerConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    private BookEntity book_1, book_2, book_3;
    @BeforeEach
    void setUp() {
        book_1 = BookEntity.builder()
                .id(1L)
                .title("Learning Java Basics")
                .description("A comprehensive guide to Java programming.")
                .publishDate(LocalDate.of(2023, 5, 15))
                .build();

        book_2 = BookEntity.builder()
                .id(2L)
                .title("Introduction to Programming")
                .description("An introductory book on programming.")
                .publishDate(LocalDate.of(2022, 3, 10))
                .build(); // No publish date

        book_3 = BookEntity.builder()
                .id(3L)
                .title("Advanced Java Programming Techniques")
                .description("A deep dive into advanced Java topics.")
                .publishDate(LocalDate.of(2024, 1, 10))
                .build();
        bookRepository.deleteAll();
    }

    @Test
    void testFindByTitleLike_whenTitleLikeIsPresent_thenReturnListOfBooks() {
        /* Arrange */
        bookRepository.save(book_1);
        bookRepository.save(book_2);
        bookRepository.save(book_3);
        /* Act */
        String searchQuery = "Java";
        List<BookEntity> bookEntityList = bookRepository.findByTitleLike('%' + searchQuery + "%");
        /* Assert */
        assertThat(bookEntityList).isNotNull();
        assertThat(bookEntityList).isNotEmpty();
        assertThat(bookEntityList.size()).isEqualTo(2);
        assertThat(bookEntityList).extracting(BookEntity::getTitle).allMatch(title -> title.contains(searchQuery));
    }

    @Test
    void testFindByTitleLike_whenTitleLikeIsNotPresent_thenReturnEmptyList() {
        /* Arrange */
        bookRepository.save(book_1);
        bookRepository.save(book_2);
        bookRepository.save(book_3);
        /* Act */
        String searchQuery = "java";
        List<BookEntity> bookEntityList = bookRepository.findByTitleLike('%' + searchQuery + "%");
        /* Assert */
        assertThat(bookEntityList).isNotNull();
        assertThat(bookEntityList).isEmpty();
    }

    @Test
    void testFindByPublishDateAfter_whenPublishDateIsAfterGivenDate_thenReturnListOfBooks() {
        /* Arrange */
        bookRepository.save(book_1);
        bookRepository.save(book_2);
        bookRepository.save(book_3);
        /* Act */
        List<BookEntity> bookEntityList = bookRepository.findByPublishDateAfter(LocalDate.of(2023, 1, 1));
        /* Assert */
        assertThat(bookEntityList).isNotNull();
        assertThat(bookEntityList).isNotEmpty();
        assertThat(bookEntityList.size()).isEqualTo(2);
    }

    @Test
    void testFindByPublishDateAfter_whenPublishDateIsNotAfterGivenDate_thenReturnEmptyList() {
        /* Arrange */
        bookRepository.save(book_1);
        bookRepository.save(book_2);
        bookRepository.save(book_3);
        /* Act */
        List<BookEntity> bookEntityList = bookRepository.findByPublishDateAfter(LocalDate.of(2024, 5, 16));
        /* Assert */
        assertThat(bookEntityList).isNotNull();
        assertThat(bookEntityList).isEmpty();
    }

}