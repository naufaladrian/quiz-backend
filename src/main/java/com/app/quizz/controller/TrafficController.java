package com.app.quizz.controller;

import com.app.quizz.dto.LoginDTO;
import com.app.quizz.dto.UserDTO;
import com.app.quizz.model.TemporaryToken;
import com.app.quizz.model.User;
import com.app.quizz.model.UserTraffic;
import com.app.quizz.response.CommonResponse;
import com.app.quizz.response.CommonResponseGenerator;
import com.app.quizz.service.UserService;
import com.app.quizz.service.UserTrafficService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class TrafficController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserTrafficService userTrafficService;

    @GetMapping( "/traffic-global-user/connect")
    public CommonResponse<TemporaryToken> connect(@RequestParam(required = false,defaultValue = "") String userId){
        return CommonResponseGenerator.successResponse(userService.connect(userId));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/traffic-user/list")
    public CommonResponse<List<UserTraffic>> getAllUserTraffic(){
        return CommonResponseGenerator.successResponse(userTrafficService.getAllUserTraffic());
    }

}
