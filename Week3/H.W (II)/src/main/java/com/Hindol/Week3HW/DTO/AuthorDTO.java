package com.Hindol.Week3HW.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AuthorDTO {
    private Long id;
    @NotNull
    @Size(min = 3, max = 100, message = "The number of characters in the name of Author should be in the range [3, 100]")
    private String name;
    private List<BookDTO> books;
}
