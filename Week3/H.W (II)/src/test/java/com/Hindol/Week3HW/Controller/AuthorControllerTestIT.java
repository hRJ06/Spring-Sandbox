package com.Hindol.Week3HW.Controller;

import com.Hindol.Week3HW.DTO.AuthorDTO;
import com.Hindol.Week3HW.Entity.AuthorEntity;
import com.Hindol.Week3HW.Repository.AuthorRepository;
import com.Hindol.Week3HW.TestContainerConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.HashMap;
import java.util.Map;

@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
class AuthorControllerTestIT {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AuthorRepository authorRepository;

    private AuthorEntity mockAuthor;
    private AuthorDTO mockAuthorDTO;
    @BeforeEach
    void setUp() {
        mockAuthor = AuthorEntity.builder()
                        .id(1L)
                        .name("Hindol Roy")
                        .build();
        mockAuthorDTO = AuthorDTO.builder()
                        .id(1L)
                        .name("Hindol Roy")
                        .build();
        authorRepository.deleteAll();
    }

    @Test
    void testGetAllAuthors_success() {
        AuthorEntity savedAuthor = authorRepository.save(mockAuthor);
        webTestClient.get()
                .uri("/author")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data[0].id").isEqualTo(savedAuthor.getId())
                .jsonPath("$.data[0].name").isEqualTo(savedAuthor.getName());
    }

    @Test
    void testGetAllAuthorsByName_success() {
        AuthorEntity savedAuthor = authorRepository.save(mockAuthor);
        webTestClient.get()
                .uri("/author?name={name}","indo")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data[0].id").isEqualTo(savedAuthor.getId())
                .jsonPath("$.data[0].name").isEqualTo(savedAuthor.getName());
    }

    @Test
    void testGetAllAuthorsByName_emptyResult() {
        webTestClient.get()
                .uri("/author?name={name}","indo")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data").isEmpty();
    }

    @Test
    void testGetAuthorById_success() {
        AuthorEntity savedAuthor = authorRepository.save(mockAuthor);
        webTestClient.get()
                .uri("/author/{id}",savedAuthor.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.id").isEqualTo(savedAuthor.getId())
                .jsonPath("$.data.name").isEqualTo(savedAuthor.getName());
    }

    @Test
    void testGetAuthorById_failure() {
        webTestClient.get()
                .uri("/author/{id}", mockAuthor.getId())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteAuthorById_success() {
        AuthorEntity savedAuthor = authorRepository.save(mockAuthor);
        webTestClient.delete()
                .uri("/author/{id}", savedAuthor.getId())
                .exchange()
                .expectStatus().isNoContent();
        webTestClient.get()
                .uri("/author/{id}", savedAuthor.getId())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteAuthorById_failure() {
        webTestClient.delete()
                .uri("/author/{id}", mockAuthor.getId())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateAuthor_success() {
        webTestClient.post()
                .uri("/author")
                .bodyValue(mockAuthorDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.data.name").isEqualTo(mockAuthorDTO.getName());
    }

    @Test
    void testUpdateAuthorById_success() {
        AuthorEntity savedAuthor = authorRepository.save(mockAuthor);
        Map<String, Object> map = new HashMap<>() {{
            put("name", "Hindol");
        }};
        webTestClient.put()
                .uri("/author/{id}", savedAuthor.getId())
                .bodyValue(map)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.name").isEqualTo(map.get("name"));
    }

    @Test
    void testUpdateAuthorById_failure() {
        Map<String, Object> map = new HashMap<>() {{
            put("name", "Hindol");
        }};
        webTestClient.put()
                .uri("/author/{id}", mockAuthor.getId())
                .bodyValue(map)
                .exchange()
                .expectStatus().isNotFound();
    }
}