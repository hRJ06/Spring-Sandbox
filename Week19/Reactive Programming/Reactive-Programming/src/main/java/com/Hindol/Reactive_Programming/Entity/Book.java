package com.Hindol.Reactive_Programming.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "books")
@Data
public class Book {
    @Id
    private Long id;
    private String name;
    private String description;
    private Long authorId;
}
