package com.example.momsytdownloaderserver.config.authentication;

import com.example.momsytdownloaderserver.exception.ErrorCode;
import com.example.momsytdownloaderserver.exception.UnauthorizedException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenValidation {

    private final JwtDecoder jwtDecoder;

    public JwtTokenValidation(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    public String validateAndDecodeToken(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            return jwt.getClaimAsString("username");
        } catch(JwtValidationException e) {
            throw new UnauthorizedException(ErrorCode.JWT_EXPIRED);
        } catch(BadJwtException e) {
            throw new UnauthorizedException(ErrorCode.JWT_CORRUPT);
        } catch(NullPointerException e) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
        }
    }
}
