package com.Hindol.Week5.Configuration;

import com.Hindol.Week5.Filter.JWTAuthFilter;
import com.Hindol.Week5.Handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration {
    private final JWTAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                authorizeHttpRequests(auth -> auth
                        .requestMatchers("/posts", "/error", "/auth/**", "/home.html").permitAll()
                        /* .requestMatchers("/posts/**").hasAnyRole("ADMIN") */
                        /* .requestMatchers("/posts/**").permitAll() */
                        .anyRequest().authenticated())
                    .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2Config -> oauth2Config
                        .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler)
                );
                /* .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(Customizer.withDefaults()); */
        return httpSecurity.build();
    }
    /*
        @Bean
        UserDetailsService myInMemoryUserDetailsService() {
            UserDetails normalUser = User.withUsername("Hindol").password(getPasswordEncoder().encode("Hindol")).roles("USER").build();
            UserDetails adminUser = User.withUsername("Jack").password(getPasswordEncoder().encode("Jack")).roles("ADMIN").build();
            return new InMemoryUserDetailsManager(normalUser, adminUser);
        }
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}