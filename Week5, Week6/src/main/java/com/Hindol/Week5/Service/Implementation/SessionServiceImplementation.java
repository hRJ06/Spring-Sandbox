package com.Hindol.Week5.Service.Implementation;

import com.Hindol.Week5.Entity.Enum.SubscriptionPlan;
import com.Hindol.Week5.Entity.SessionEntity;
import com.Hindol.Week5.Entity.UserEntity;
import com.Hindol.Week5.Repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static com.Hindol.Week5.Entity.Enum.SubscriptionPlan.*;

@Service
@RequiredArgsConstructor
public class SessionServiceImplementation {
    private final SessionRepository sessionRepository;
    /* private final int SESSION_LIMIT = 1 */;
    public void generateNewSession(UserEntity userEntity, String refreshToken) {
        List<SessionEntity> userSessions = sessionRepository.findByUserEntity(userEntity);
        SubscriptionPlan subscriptionPlan = userEntity.getSubscriptionPlan();

        int sessionLimit = 0;

        if(subscriptionPlan.equals(FREE)) {
            sessionLimit = 1;
        }
        else if(subscriptionPlan.equals(BASIC)) {
            sessionLimit = 2;
        }
        else if(subscriptionPlan.equals(PREMIUM)) {
            sessionLimit = 3;
        }

        if(userSessions.size() >= sessionLimit) {
            userSessions.sort(Comparator.comparing(SessionEntity::getLastUsedAt));
            SessionEntity leastRecentlyUsedSession = userSessions.get(0);
            sessionRepository.delete(leastRecentlyUsedSession);
        }
        SessionEntity sessionEntity = SessionEntity.builder()
                .userEntity(userEntity)
                .refreshToken(refreshToken)
                .build();
        sessionRepository.save(sessionEntity);
    }
    public void validateSession(String refreshToken) {
        SessionEntity sessionEntity = sessionRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new SessionAuthenticationException("No Session found with Refresh Token " + refreshToken));
        sessionEntity.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(sessionEntity);
    }
    public void updateSession(String oldRefreshToken, String newRefreshToken) {
        SessionEntity oldSession = sessionRepository.findByRefreshToken(oldRefreshToken).orElseThrow(() -> new SessionAuthenticationException("No Session found with Refresh Token " + newRefreshToken));
        oldSession.setLastUsedAt(LocalDateTime.now());
        oldSession.setRefreshToken(newRefreshToken);
        sessionRepository.save(oldSession);

    }
}
