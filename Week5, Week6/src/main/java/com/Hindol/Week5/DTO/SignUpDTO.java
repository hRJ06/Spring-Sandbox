package com.Hindol.Week5.DTO;

import com.Hindol.Week5.Entity.Enum.Permission;
import com.Hindol.Week5.Entity.Enum.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {
    private String name;
    private String email;
    private String password;
    private Set<Role> roles;
    private Set<Permission> permissions;
}