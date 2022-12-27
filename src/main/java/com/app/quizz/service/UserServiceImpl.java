package com.app.quizz.service;


import com.app.quizz.dto.LoginDTO;
import com.app.quizz.dto.UserDTO;
import com.app.quizz.exception.BusinessException;
import com.app.quizz.exception.NotFoundException;
import com.app.quizz.jwt.JwtProvider;
import com.app.quizz.model.School;
import com.app.quizz.model.TemporaryToken;
import com.app.quizz.model.User;
import com.app.quizz.model.UserTraffic;
import com.app.quizz.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private String pass = "quizkeyprivate";
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private UserRepository userRepository;
    private TemporaryTokenRepository temporaryTokenRepository;
    private UserTrafficRepository userTrafficRepository;
    private UserDetailServiceImpl userDetailservice;
    private RoleRepository roleRepository;

    private SchoolRepository schoolRepository;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserRepository userRepository, TemporaryTokenRepository temporaryTokenRepository, UserDetailServiceImpl userDetailservice, RoleRepository roleRepository, UserTrafficRepository userTrafficRepository, SchoolRepository schoolRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.userDetailservice = userDetailservice;
        this.temporaryTokenRepository = temporaryTokenRepository;
        this.roleRepository = roleRepository;
        this.userTrafficRepository = userTrafficRepository;
        this.schoolRepository = schoolRepository;
    }

    //GET USER IP ADDRESS
    public static String getClientIpAddressIfServletRequestExist() {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return "0.0.0.0";
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
        if (ip.equals("0:0:0:0:0:0:0:1")) ip = "127.0.0.1";
        Assert.isTrue(ip.chars().filter($ -> $ == '.').count() == 3, "Illegal IP: " + ip);
        return ip;
    }

    //ADD USER TRAFFIC
    public UserTraffic addUserTraffic(User user) {
        UserTraffic userTraffic = new UserTraffic();
        userTraffic.setVisitors(1);
        userTraffic.setUserId(user);
        return userTrafficRepository.save(userTraffic);
    }

    public User increaseUserTraffic(String userId) {
        User findUserId = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User Id Not Found"));
        Optional<UserTraffic> userTrafficId = userTrafficRepository.findByUserId(findUserId);
        if (userTrafficId.isPresent()) {
            userTrafficId.get().setVisitors(userTrafficId.get().getVisitors() + 1);
            userTrafficRepository.save(userTrafficId.get());
        }
        return findUserId;

    }


    //ADD USER
    public User addUser() {
        User user = new User();
        user.setUsername("guest_");
        user.setPassword(passwordEncoder.encode(pass));
        user.setRoleId(roleRepository.findById(2).get());
        user.setIpAddress(getClientIpAddressIfServletRequestExist());
        User saveUser = userRepository.save(user);
        saveUser.setUsername(saveUser.getUsername() + saveUser.getId().split("-")[0]);
        addUserTraffic(saveUser);
        return userRepository.save(saveUser);
    }

    @Override
    public User addNewAdmin(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoleId(roleRepository.findById(1).get());
        user.setUrlProfile(userDTO.getUrlProfile());
        user.setIpAddress(getClientIpAddressIfServletRequestExist());
        return userRepository.save(user);
    }

    public static Authentication authentication ;
    public static UserDetails userDetails;
    @Override
    public TemporaryToken loginAdmin(User user) {

        userDetails=null;
        authentication=null;
        try {
            authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
             userDetails= userDetailservice.loadUserByUsername(user.getUsername());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            throw new BadCredentialsException("Username or Password NOT FOUND");

        }
        User getUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new NotFoundException("getUser Error"));
        if (getUser.getRoleId().getId() != 1) throw new BadCredentialsException("Admin Only");
        String jwt = jwtProvider.generateJwtToken(authentication);
        var tokenAlready = temporaryTokenRepository.findByUserId(getUser);
        if (tokenAlready.isPresent()) {
            temporaryTokenRepository.delete(tokenAlready.get());
        }
        TemporaryToken temporaryToken = new TemporaryToken();
        temporaryToken.setToken(jwt);
        temporaryToken.setExpiredDate(new Date(System.currentTimeMillis() + 900000));
        temporaryToken.setUserId(user);
        return temporaryTokenRepository.save(temporaryToken);
    }

    @Transactional
    @Override
    public TemporaryToken connect(String userId) {
        User user;
        if (!userId.isEmpty()) {
            user = increaseUserTraffic(userId);
        } else {
            user = addUser();
        }
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), pass));
            UserDetails userDetails = userDetailservice.loadUserByUsername(user.getUsername());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtProvider.generateJwtToken(authentication);
            User getUser = userRepository.findByUsername(userDetails.getUsername()).get();
            var tokenAlready = temporaryTokenRepository.findByUserId(getUser);
            if (tokenAlready.isPresent()) {
                temporaryTokenRepository.delete(tokenAlready.get());
            }
            TemporaryToken temporaryToken = new TemporaryToken();
            temporaryToken.setToken(jwt);
            temporaryToken.setExpiredDate(new Date(System.currentTimeMillis() + 900000));
            temporaryToken.setUserId(user);
            return temporaryTokenRepository.save(temporaryToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Username or Password NOT FOUND");
        }
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }

    @Override
    public User updateUser(String id, User user) {
        User updateDataUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        updateDataUser.setEmail(user.getEmail());
        updateDataUser.setUsername(user.getUsername());
        updateDataUser.setPassword(user.getPassword());
        updateDataUser.setUrlProfile(user.getUrlProfile());
        return userRepository.save(updateDataUser);
    }

    @Override
    public User regToSchool(String userId, String schoolId) {
        User findUserId = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User Id Not Found"));
        School findSchoolId = schoolRepository.findById(schoolId).orElseThrow(() -> new NotFoundException("School Id Not Found"));
        findUserId.setSchoolId(findSchoolId);
        return userRepository.save(findUserId);
    }
}
