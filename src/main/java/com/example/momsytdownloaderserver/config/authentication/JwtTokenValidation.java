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
            throw new UnauthorizedException(ErrorCode.FOURXXCODE, "토큰 기간이 지났습니다.");
        } catch(BadJwtException e) {
            throw new UnauthorizedException(ErrorCode.FOURXXCODE, "변조된 토큰입니다.");
        } catch(NullPointerException e) {
            throw new UnauthorizedException(ErrorCode.FOURXXCODE, "권한이 없는 토큰입니다.");
        }
    }
}
