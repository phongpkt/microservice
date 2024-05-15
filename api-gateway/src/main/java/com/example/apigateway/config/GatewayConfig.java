package com.example.apigateway.config;

import com.example.apigateway.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class GatewayConfig {
    @Autowired
    private JwtAuthenticationFilter filter;
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("hotel-service", r -> r.path("/api/hotel/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8081")) //lb:hotel
                .route("room-service", r -> r.path("/api/room/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8082"))
                .route("roomType-service", r -> r.path("/api/roomType/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8082"))
                .route("auth-service", r -> r.path("/api/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://localhost:8090"))
                .build();
    }

}
