package com.Hindol.Auditing.Auth;

import org.modelmapper.internal.bytebuddy.dynamic.DynamicType;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImplementation implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Hindol Roy");
    }
}
