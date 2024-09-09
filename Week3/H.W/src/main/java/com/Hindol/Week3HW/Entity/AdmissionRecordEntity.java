package com.Hindol.Week3HW.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Entity
@Getter
@Setter
@Table(name = "admission_record")
@Audited
public class AdmissionRecordEntity extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer fees;
    @OneToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;
}
