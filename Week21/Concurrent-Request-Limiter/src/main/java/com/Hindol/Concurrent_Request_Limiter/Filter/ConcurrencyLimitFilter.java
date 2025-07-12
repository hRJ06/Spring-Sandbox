package com.Hindol.Concurrent_Request_Limiter.Filter;

import com.Hindol.Concurrent_Request_Limiter.Model.ConcurrencyLimiter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ConcurrencyLimitFilter extends OncePerRequestFilter {

    private final ConcurrencyLimiter limiter;

    @Autowired
    public ConcurrencyLimitFilter(ConcurrencyLimiter limiter) {
        this.limiter = limiter;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!limiter.tryAcquire()) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many Concurrent Request. Please Try Again Later.");
            return;
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            limiter.release();
        }
    }
}
