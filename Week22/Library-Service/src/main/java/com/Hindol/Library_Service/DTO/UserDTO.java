package com.Hindol.Library_Service.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private List<Long> rentedBook;
    private List<BookDTO> book;
}
