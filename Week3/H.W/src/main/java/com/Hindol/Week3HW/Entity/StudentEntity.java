package com.Hindol.Week3HW.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToOne(mappedBy = "student")
    private AdmissionRecordEntity admissionRecord;

    @ManyToMany(mappedBy = "students")
    private Set<SubjectEntity> subjects;

    @ManyToMany(mappedBy = "students")
    private Set<ProfessorEntity> professors;
}
