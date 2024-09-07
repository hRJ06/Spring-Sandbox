package com.Hindol.Week3HW.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private AdmissionRecordEntity admissionRecord;

    @ManyToMany(mappedBy = "students")
    private Set<SubjectEntity> subjects;

    @ManyToMany(mappedBy = "students")
    private Set<ProfessorEntity> professors;
}
