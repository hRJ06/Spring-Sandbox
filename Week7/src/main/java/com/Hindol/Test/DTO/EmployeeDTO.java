package com.Hindol.Test.DTO;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String email;
    private String name;
    private Long salary;
}
