package com.example.momsytdownloaderserver.config.authentication;

import com.example.momsytdownloaderserver.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.momsytdownloaderserver.entity.Role.ROLE_MEMBER;

@Component
public class JwtAuthenticationManager implements AuthenticationManager {

    private final JwtTokenGenerator jwtTokenGenerator;

    public JwtAuthenticationManager(JwtTokenGenerator jwtTokenGenerator) {
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BearerTokenAuthenticationToken jwt = (BearerTokenAuthenticationToken) authentication;
        User user = jwtTokenGenerator.validateTokenAndFindUser(jwt.getToken());
        List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority(ROLE_MEMBER.name()));
        return new UsernamePasswordAuthenticationToken(user, "", grantedAuthorityList);
    }
}
