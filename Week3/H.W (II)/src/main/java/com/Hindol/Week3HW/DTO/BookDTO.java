package com.Hindol.Week3HW.DTO;

import com.Hindol.Week3HW.Entity.AuthorEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class BookDTO {
    private Long id;
    private String title;
    private String description;
    private AuthorEntity author;
    private LocalDate publishDate;
}
