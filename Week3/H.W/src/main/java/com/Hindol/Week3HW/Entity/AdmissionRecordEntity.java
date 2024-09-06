package com.Hindol.Week3HW.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "admission_record")
public class AdmissionRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Integer fees;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private StudentEntity student;
}
