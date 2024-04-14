package com.example.momsytdownloaderserver.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@PropertySource("classpath:env.properties")
public class JwtEncodingConfig {

    @Value("${JWT_SECRET}")
    private String jwtKey;
    private SecretKeySpec secretKeySpec;

    @PostConstruct
    public void setSecretKeySpec() {
        this.secretKeySpec = new SecretKeySpec(this.jwtKey.getBytes(), "HmacSHA256");
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(this.secretKeySpec).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        ImmutableSecret<SecurityContext> secret = new ImmutableSecret<>(this.secretKeySpec);
        return new NimbusJwtEncoder(secret);
    }
}
