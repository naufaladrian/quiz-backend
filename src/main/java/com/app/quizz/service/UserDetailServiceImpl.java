package com.app.quizz.service;

import com.app.quizz.model.User;
import com.app.quizz.model.UserPrincipal;
import com.app.quizz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found with -> username or email : " +username));
        return UserPrincipal.build(user);
    }
}
