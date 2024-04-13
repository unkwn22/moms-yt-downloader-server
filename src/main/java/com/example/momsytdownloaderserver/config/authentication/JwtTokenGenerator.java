package com.example.momsytdownloaderserver.config.authentication;

import com.example.momsytdownloaderserver.entity.User;
import com.example.momsytdownloaderserver.entity.UserInteraction;
import com.example.momsytdownloaderserver.exception.ErrorCode;
import com.example.momsytdownloaderserver.exception.UnauthorizedException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
public class JwtTokenGenerator {

    private final JwtEncoder jwtEncoder;
    private final UserInteraction userInteraction;
    private final JwtTokenValidation jwtTokenValidation;

    public JwtTokenGenerator(JwtEncoder jwtEncoder, UserInteraction userInteraction, JwtTokenValidation jwtTokenValidation) {
        this.jwtEncoder = jwtEncoder;
        this.userInteraction = userInteraction;
        this.jwtTokenValidation = jwtTokenValidation;
    }

    public String createToken(User user) {
        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256)
            .type("JWT")
            .build();
        JwtClaimsSet claims = JwtClaimsSet.builder()
            .expiresAt(Instant.now().plus(365L, ChronoUnit.DAYS))
            .subject(user.getUsername())
            .claim("username", user.getUsername())
            .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public User validateTokenAndFindUser(String token) {
        String username = jwtTokenValidation.validateAndDecodeToken(token);
        Optional<User> searchedUser = userInteraction.findByUserName(username);
        if(searchedUser.isEmpty()) throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
        User foundUser = searchedUser.get();
        if(foundUser.getAuthorizeStatus() == 0) throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
        return foundUser;
    }
}
