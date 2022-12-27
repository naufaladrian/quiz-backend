package com.app.quizz.jwt;


import com.app.quizz.model.UserPrincipal;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtProvider {


    @Value("SecretKey")
    private String jwtSecret;

    @Value("1800000")
    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication) {
        UserPrincipal adminPrincipal = (UserPrincipal) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((adminPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            System.err.println("Invalid JWT token: {}"+ e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("JWT token is expired: {}"+ e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("JWT token is unsupported: {}"+ e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string is empty: {}"+ e.getMessage());
        }
        return false;
    }

}
