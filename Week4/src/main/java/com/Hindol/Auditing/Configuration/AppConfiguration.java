package com.Hindol.Auditing.Configuration;

import com.Hindol.Auditing.Auth.AuditorAwareImplementation;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getAuditorAwareImplementation")
public class AppConfiguration {
    @Bean
    ModelMapper getModelMapper() {
        return new ModelMapper();
    }
    @Bean
    AuditorAware<String> getAuditorAwareImplementation() {
        return new AuditorAwareImplementation();
    }
}