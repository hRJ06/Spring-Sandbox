package com.Hindol.Week5.Repository;

import com.Hindol.Week5.Entity.SessionEntity;
import com.Hindol.Week5.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    List<SessionEntity> findByUserEntity(UserEntity userEntity);
    Optional<SessionEntity> findByRefreshToken(String refreshToken);
}
