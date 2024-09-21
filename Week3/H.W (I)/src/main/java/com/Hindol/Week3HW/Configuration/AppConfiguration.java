package com.Hindol.Week3HW.Configuration;

import com.Hindol.Week3HW.Auth.AuditorAwareImplementation;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getAuditorAwareImplementation")
public class AppConfiguration {
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
    @Bean
    AuditorAware<String> getAuditorAwareImplementation() {
        return new AuditorAwareImplementation();
    }
    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
