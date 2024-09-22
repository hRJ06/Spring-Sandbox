package com.Hindol.Week3HW.Repository;

import com.Hindol.Week3HW.Entity.SessionEntity;
import com.Hindol.Week3HW.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    List<SessionEntity> findByUserEntity(UserEntity userEntity);
    Optional<SessionEntity> findByToken(String token);
}
