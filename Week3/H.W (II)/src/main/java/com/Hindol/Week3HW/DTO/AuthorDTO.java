package com.Hindol.Week3HW.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    private Long id;
    @NotNull
    @Size(min = 3, max = 100, message = "The number of characters in the name of Author should be in the range [3, 100]")
    private String name;
    @JsonIgnore
    private List<BookDTO> books;
}
