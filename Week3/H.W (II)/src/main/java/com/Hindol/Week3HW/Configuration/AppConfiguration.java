package com.Hindol.Week3HW.Configuration;

import com.Hindol.Week3HW.Auth.AuditAwareImplementation;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getAuditAwareImplementation")
public class AppConfiguration {
    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }
    @Bean
    AuditorAware<String> getAuditAwareImplementation() {
        return new AuditAwareImplementation();
    }
}
