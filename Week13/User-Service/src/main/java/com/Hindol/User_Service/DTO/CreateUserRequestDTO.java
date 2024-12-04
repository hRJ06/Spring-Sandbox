package com.Hindol.User_Service.DTO;

import lombok.Data;

@Data
public class CreateUserRequestDTO {
    private Long id;
    private String name;
    private String email;
}
