package com.app.quizz.dto;


import com.app.quizz.model.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String urlProfile;

}
