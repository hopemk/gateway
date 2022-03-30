package com.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes().build();
    }

    @Bean
    public RouteLocator myRoutes00(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                .path("/get")
                .filters(f -> f.addRequestHeader("Hello", "World"))
                .uri("http://httpbin.org:80"))
                .build();
    }

    @Bean
    public RouteLocator myRoutes0(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                .path("/get")
                .filters(f -> f.addRequestHeader("Hello", "World"))
                .uri("http://httpbin.org:80"))
                .route(p -> p
                .host("*.circuitbreaker.com")
                .filters(f -> f.circuitBreaker(config -> config.setName("mycmd")))
                .uri("http://httpbin.org:80")).
                build();
    }

    @Bean
    public RouteLocator myRoutes1(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                .path("/get")
                .filters(f -> f.addRequestHeader("Hello", "World"))
                .uri("http://httpbin.org:80"))
                .route(p -> p
                .host("*.circuitbreaker.com")
                .filters(f -> f.circuitBreaker(config -> config
                .setName("mycmd")
                .setFallbackUri("forward:/fallback")))
                .uri("http://httpbin.org:80"))
                .build();
    }

}
