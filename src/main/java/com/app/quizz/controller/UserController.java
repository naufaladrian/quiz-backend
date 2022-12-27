package com.app.quizz.controller;

import com.app.quizz.dto.LoginDTO;
import com.app.quizz.dto.UserDTO;
import com.app.quizz.model.TemporaryToken;
import com.app.quizz.model.User;
import com.app.quizz.response.CommonResponse;
import com.app.quizz.response.CommonResponseGenerator;
import com.app.quizz.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/user")
public class UserController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/{id}")
    public CommonResponse<User> getUserById (@PathVariable("id") String id){
        return CommonResponseGenerator.successResponse(userService.getUserById(id));
    }
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}")
    public CommonResponse<User> updateUser(@PathVariable("id") String id,@RequestBody UserDTO user){
        return CommonResponseGenerator.successResponse(userService.updateUser(id, modelMapper.map(user, User.class)));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}/update-school/{school_id}")
    public CommonResponse<User> regUserToSchool(@PathVariable("id")String userId, @PathVariable("school_id")String schoolId){
        return CommonResponseGenerator.successResponse(userService.regToSchool(userId, schoolId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/signup-admin-school")
    public CommonResponse<User> newAdmin(@RequestBody UserDTO user){
        return CommonResponseGenerator.successResponse(userService.addNewAdmin(user));
    }

    @PostMapping("/admin/login")
    public CommonResponse<TemporaryToken> loginAdmin(@RequestBody LoginDTO userDTO){
        return CommonResponseGenerator.successResponse(userService.loginAdmin(modelMapper.map(userDTO, User.class)));
    }
}
