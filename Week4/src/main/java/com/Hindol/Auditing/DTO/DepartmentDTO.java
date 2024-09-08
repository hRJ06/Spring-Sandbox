package com.Hindol.Auditing.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    private Long Id;
    private String title;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private Long no;
    private String password;
}
