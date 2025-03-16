package com.example.OnlineFoodOrdering.service;

import com.example.OnlineFoodOrdering.exception.ResourceNotFoundException;
import com.example.OnlineFoodOrdering.model.Token;
import com.example.OnlineFoodOrdering.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public record TokenService(TokenRepository tokenRepository) {
    public int saveToken(Token token) {
        Optional<Token> optional = tokenRepository.findByUsername(token.getUsername());
        if (optional.isEmpty()){
            tokenRepository.save(token);
            return token.getId();
        } else {
            Token currentToken = optional.get();
            currentToken.setAccessToken(token.getAccessToken());
            currentToken.setRefreshToken(token.getRefreshToken());
            currentToken.setResetToken(token.getResetToken());
            tokenRepository.save(currentToken);
            return currentToken.getId();
        }
    }

    public Token getByUsername(String username) {
        return tokenRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Token not found"));
    }

    public String deleteToken(Token token) {
        tokenRepository.delete(token);
        return "Token deleted";
    }
}
