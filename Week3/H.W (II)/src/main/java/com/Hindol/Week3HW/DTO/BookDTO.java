package com.Hindol.Week3HW.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    @NotNull
    @Size(min = 3, max = 100, message = "The number of characters in the title of Book should be in the range [3, 100]")
    private String title;
    @NotNull
    @Size(min = 10, max = 100, message = "The number of characters in the description of Book should be in the range [10, 100]")
    private String description;
    @JsonIgnore
    private AuthorDTO author;
    @PastOrPresent(message = "Book publish date should be Past or Present")
    private LocalDate publishDate;
}
