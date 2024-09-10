package com.Hindol.Week3HW.DTO;

import com.Hindol.Week3HW.Entity.BookEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AuthorDTO {
    private Long id;
    private String name;
    private List<BookEntity> books;
}
