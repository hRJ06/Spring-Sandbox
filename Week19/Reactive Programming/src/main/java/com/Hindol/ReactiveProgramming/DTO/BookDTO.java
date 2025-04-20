package com.Hindol.ReactiveProgramming.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String name;
    private String description;
    private Long authorId;
}
