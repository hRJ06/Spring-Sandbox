package com.Hindol.Week3HW.Repository;

import com.Hindol.Week3HW.Entity.AuthorEntity;
import com.Hindol.Week3HW.TestContainerConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;


@DataJpaTest
@Import(TestContainerConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    private AuthorEntity author_1, author_2, author_3;

    @BeforeEach
    void setUp() {
        author_1 = AuthorEntity.builder()
                .id(1L)
                .name("Hindol Roy")
                .build();
        author_2 = AuthorEntity.builder()
                .id(2L)
                .name("John Doe")
                .build();
        author_3 = AuthorEntity.builder()
                .id(3L)
                .name("HINDOL ROY")
                .build();
        authorRepository.deleteAll();
    }

    @Test
    void testFindByNameLike_whenNameLikeIsPresent_thenReturnListOfAuthors() {
        /* Arrange */
        authorRepository.save(author_1);
        authorRepository.save(author_2);
        /* Act */
        String searchQuery = "ol";
        List<AuthorEntity> authorEntityList = authorRepository.findByNameLike('%' + searchQuery + '%');
        /* Assert */
        assertThat(authorEntityList).isNotNull();
        assertThat(authorEntityList).isNotEmpty();
        assertThat(authorEntityList.size()).isEqualTo(1);
        assertThat(authorEntityList).extracting(AuthorEntity::getName).allMatch(name -> name.contains(searchQuery));
    }

    @Test
    void testFindByNameLike_whenNameLikeIsNotPresent_thenReturnEmptyList() {
        /* Arrange */
        authorRepository.save(author_2);
        authorRepository.save(author_3);
        /* Act */
        String searchQuery = "ol";
        List<AuthorEntity> authorEntityList = authorRepository.findByNameLike('%' + searchQuery + '%');
        /* Assert */
        assertThat(authorEntityList).isNotNull();
        assertThat(authorEntityList).isEmpty();
    }

}