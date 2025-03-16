package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.exception.InvalidDataException;
import com.example.OnlineFoodOrdering.service.impl.JwtService;
import com.example.OnlineFoodOrdering.util.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.example.OnlineFoodOrdering.config.JwtConstant.*;
import static com.example.OnlineFoodOrdering.util.TokenType.ACCESS_TOKEN;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public String generateToken(UserDetails username) {
        return generateToken(new HashMap<>(), username);
    }

    @Override
    public String extractUsername(String token, TokenType tokenType) {
        return extractClaim(token, tokenType, Claims::getSubject);
    }

    private <T> T extractClaim(String token, TokenType tokenType, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token, tokenType);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, TokenType tokenType) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(tokenType))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean isValid(String token, UserDetails userDetails, TokenType tokenType) {
        final String username = extractUsername(token, tokenType);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, tokenType));
    }

    private boolean isTokenExpired(String token, TokenType tokenType) {
        return extractExpiration(token, tokenType).before(new Date());
    }

    private Date extractExpiration(String token, TokenType tokenType) {
        return extractClaim(token, tokenType, Claims::getExpiration);
    }

    @Override
    public String generateRefreshToken(UserDetails user) {
        return "";
    }

    @Override
    public String generateResetToken(UserDetails user) {
        return "";
    }

    private String generateToken(Map<String, Object> claims, UserDetails username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(EXPIRY_HOURS)))
                .signWith(getKey(ACCESS_TOKEN), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(TokenType tokenType) {
        switch (tokenType) {
            case ACCESS_TOKEN:
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
            case REFRESH_TOKEN:
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(REFRESH_KEY));
            case RESET_TOKEN:
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(RESET_KEY));
            default:
                throw new InvalidDataException("Invalid token type");
        }
    }
}
