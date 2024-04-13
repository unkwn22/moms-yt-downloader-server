package com.example.momsytdownloaderserver.config.security;

import com.example.momsytdownloaderserver.config.authentication.JwtAuthenticationManager;
import com.example.momsytdownloaderserver.config.filter.CorsFilter;
import com.example.momsytdownloaderserver.config.filter.JwtAuthenticationExceptionFilter;
import com.example.momsytdownloaderserver.config.filter.JwtAuthenticationFilter;
import com.example.momsytdownloaderserver.handler.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationManager jwtAuthenticationManager;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationExceptionFilter jwtAuthenticationExceptionFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(JwtAuthenticationManager jwtAuthenticationManager, JwtAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationExceptionFilter jwtAuthenticationExceptionFilter, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.jwtAuthenticationManager = jwtAuthenticationManager;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationExceptionFilter = jwtAuthenticationExceptionFilter;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        http.authenticationManager(jwtAuthenticationManager);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationExceptionFilter, JwtAuthenticationFilter.class)
            .exceptionHandling(authenticationManager -> authenticationManager.accessDeniedHandler(customAccessDeniedHandler));
        http.httpBasic(HttpBasicConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(CsrfConfigurer::disable);
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(new CorsFilter(), SessionManagementFilter.class);
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http.headers(headers -> headers.xssProtection(HeadersConfigurer.XXssConfig::disable));

        // TODO add apis
//        http.authorizeHttpRequests(authorize ->
//            authorize.requestMatchers("", "").hasRole("MEMBER").anyRequest().permitAll()
//        );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
