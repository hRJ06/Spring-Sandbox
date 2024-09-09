package com.Hindol.Week3HW.Controller;

import com.Hindol.Week3HW.Entity.ProfessorEntity;
import com.Hindol.Week3HW.Entity.StudentEntity;
import com.Hindol.Week3HW.Entity.SubjectEntity;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/audit")
public class AuditController {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private AuditReader getAuditReader() {
        return AuditReaderFactory.get(entityManagerFactory.createEntityManager());
    }
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentEntity>> getStudentRevisions(@PathVariable Integer studentId) {
        AuditReader auditReader = getAuditReader();
        List<Number> revisions = auditReader.getRevisions(StudentEntity.class, studentId);
        return ResponseEntity.ok(revisions.stream()
                .map(revision -> auditReader.find(StudentEntity.class, studentId, revision))
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<ProfessorEntity>> getProfessorRevisions(@PathVariable Integer professorId) {
        AuditReader auditReader = getAuditReader();
        List<Number> revisions = auditReader.getRevisions(ProfessorEntity.class, professorId);
        return ResponseEntity.ok(revisions.stream()
                .map(revision -> auditReader.find(ProfessorEntity.class, professorId, revision))
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<SubjectEntity>> getSubjectRevisions(@PathVariable Integer subjectId) {
        AuditReader auditReader = getAuditReader();
        List<Number> revisions = auditReader.getRevisions(SubjectEntity.class, subjectId);
        return ResponseEntity.ok(revisions.stream()
                .map(revision -> auditReader.find(SubjectEntity.class, subjectId, revision))
                .collect(Collectors.toList())
        );
    }
}

