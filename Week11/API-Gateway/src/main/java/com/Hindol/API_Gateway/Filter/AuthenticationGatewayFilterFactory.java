package com.Hindol.API_Gateway.Filter;

import com.Hindol.API_Gateway.Service.JWTService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final JWTService jwtService;

    public AuthenticationGatewayFilterFactory(JWTService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if(!config.isEnabled) return chain.filter(exchange);
            String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if(authorizationHeader == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            String token = authorizationHeader.split("Bearer ")[1];
            Long userId = jwtService.getUserIdFromToken(token);
            exchange.getRequest()
                    .mutate()
                    .header("X-User-Id", userId.toString())
                    .build();
            return chain.filter(exchange);
        });
    }

    @Data
    public static class Config {
        private boolean isEnabled;
    }
}
