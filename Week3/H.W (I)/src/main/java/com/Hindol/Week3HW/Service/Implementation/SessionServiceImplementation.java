package com.Hindol.Week3HW.Service.Implementation;

import com.Hindol.Week3HW.Entity.SessionEntity;
import com.Hindol.Week3HW.Entity.UserEntity;
import com.Hindol.Week3HW.Repository.SessionRepository;
import com.Hindol.Week3HW.Service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImplementation implements SessionService {
    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT = 1;
    @Override
    public void generateNewSession(UserEntity userEntity, String token) {
        List<SessionEntity> sessionEntityList = sessionRepository.findByUserEntity(userEntity);
        if(sessionEntityList.size() == SESSION_LIMIT) {
            sessionEntityList.sort(Comparator.comparing(SessionEntity::getLastUsedAt));
            SessionEntity leastRecentlyUsedSession = sessionEntityList.get(0);
            sessionRepository.delete(leastRecentlyUsedSession);
        }
        SessionEntity session = SessionEntity.builder().token(token).userEntity(userEntity).build();
        sessionRepository.save(session);
    }

    @Override
    public void validateSession(String token) {
        SessionEntity session = sessionRepository.findByToken(token).orElseThrow(() -> new SessionAuthenticationException("No Session found for token " + token));
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }
}
