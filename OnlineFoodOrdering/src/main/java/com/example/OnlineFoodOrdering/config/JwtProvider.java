package com.example.OnlineFoodOrdering.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {
    private SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roles = populateRoles(authorities);
        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + JwtConstant.EXPIRATION_TIME))
//                .setSubject(authentication.getName())
                .claim("email", authentication.getName())
                .claim("authorities", roles)
                .signWith(secretKey)
                .compact();
        return jwt;
    }

    public String getEmailFromToken(String jwt) {
        jwt = jwt.substring(7);

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        String email = String.valueOf(claims.get("email"));
        return email;
    }

    private String populateRoles(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auths = new HashSet<>();

        for (GrantedAuthority authority : authorities) {
            auths.add(authority.getAuthority());
        }

        return String.join(",", auths);
    }
}
