package com.app.quizz.controller;

import com.app.quizz.config.AuthenticationFacade;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestingController {
    @GetMapping("/user")
    public String usertest(){
        return "user";
    }
    @GetMapping("/admin")
    public String admin(){
        return null;
    }
}
