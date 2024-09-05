package com.Hindol.Week3HW.Repository;

import com.Hindol.Week3HW.Entity.AdmissionRecordEntity;
import com.Hindol.Week3HW.Entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdmissionRecordRepository extends JpaRepository<AdmissionRecordEntity, Long> {
    Optional<AdmissionRecordEntity> findByStudent(StudentEntity student);
}
