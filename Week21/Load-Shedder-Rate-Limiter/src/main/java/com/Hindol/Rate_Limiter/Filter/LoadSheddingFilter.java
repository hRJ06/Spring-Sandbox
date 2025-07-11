package com.Hindol.Rate_Limiter.Filter;

import com.Hindol.Rate_Limiter.Service.LoadShedderService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class LoadSheddingFilter extends OncePerRequestFilter {
    @Autowired
    private LoadShedderService loadShedderService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String type = request.getHeader("X-Request-Type");
        boolean isCritical = "Critical".equalsIgnoreCase(type);

        if(!isCritical && loadShedderService.isOverLoaded()) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
