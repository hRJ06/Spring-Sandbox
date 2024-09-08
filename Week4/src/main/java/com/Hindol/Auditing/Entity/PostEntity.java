package com.Hindol.Auditing.Entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Audited
/* @EntityListeners(AuditingEntityListener.class) */
public class PostEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    /* @NotAudited */
    private String description;

    /*
        @CreatedDate
        @Column(nullable = false, updatable = false)
        private LocalDateTime createdDate;

        @LastModifiedDate
        private LocalDateTime updatedDate;

        @CreatedBy
        private String createdBy;

        @LastModifiedBy
        private String updatedBy;
     */
}
