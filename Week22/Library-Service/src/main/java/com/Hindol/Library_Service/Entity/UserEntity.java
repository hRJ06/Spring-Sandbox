package com.Hindol.Library_Service.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ElementCollection
    @CollectionTable(
            name = "user_rented_books",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "book_id")
    private List<Long> rentedBook;
}
