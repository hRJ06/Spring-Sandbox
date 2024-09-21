package com.Hindol.Week3HW.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
    private final String CLASS_NAME = "LoggingFilter";

    private void logRequest(HttpServletRequest request) {
        log.info("INCOMING REQUEST : {} {} FROM {} INSIDE {}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr(), CLASS_NAME);
    }


    private void logResponse(HttpServletResponse response) {
        log.info("OUTGOING RESPONSE: STATUS={} INSIDE {}", response.getStatus(), CLASS_NAME);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logRequest(request);
        filterChain.doFilter(request, response);
        logResponse(response);
    }
}
