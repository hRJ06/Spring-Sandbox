package com.Hindol.Week3HW.Repository;

import com.Hindol.Week3HW.Entity.AdmissionRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmissionRecordRepository extends JpaRepository<AdmissionRecordEntity, Long> {
}
