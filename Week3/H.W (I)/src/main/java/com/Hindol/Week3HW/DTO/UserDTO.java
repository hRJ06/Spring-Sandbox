package com.Hindol.Week3HW.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDTO {
    private String name;
    private String email;
    private String password;
}
