package com.app.quizz.config;

import com.app.quizz.model.User;
import com.app.quizz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    @Autowired
    private  UserRepository userRepository;
    public User getAuthentication() {
        String username=SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(()->new BadCredentialsException("user not found"));
    }
}
