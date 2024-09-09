package com.Hindol.Week3HW.Auth;


import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditAwareImplementation implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Hindol Roy");
    }
}
