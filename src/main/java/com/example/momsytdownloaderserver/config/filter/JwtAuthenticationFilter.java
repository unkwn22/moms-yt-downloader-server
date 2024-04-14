package com.example.momsytdownloaderserver.config.filter;

import com.example.momsytdownloaderserver.config.authentication.JwtTokenValidation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String headerString = "Authorization";
    private final String tokenPrefix = "Bearer ";
    private final JwtTokenValidation jwtTokenValidation;

    public JwtAuthenticationFilter(JwtTokenValidation jwtTokenValidation) {
        this.jwtTokenValidation = jwtTokenValidation;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> header = Optional.ofNullable(request.getHeader(headerString));
        if(header.isPresent() && header.get().startsWith(tokenPrefix)) {
            String subStringedToken = header.get().replace(tokenPrefix, "");
            jwtTokenValidation.validateAndDecodeToken(subStringedToken);
        }
        filterChain.doFilter(request, response);
    }
}
