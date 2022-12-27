package com.app.quizz.service;

import com.app.quizz.dto.LoginDTO;
import com.app.quizz.dto.UserDTO;
import com.app.quizz.model.TemporaryToken;
import com.app.quizz.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    TemporaryToken connect(String userId);

    User getUserById (String id);
    User updateUser(String id, User user);

    User regToSchool(String userId, String schoolId);

    User addNewAdmin(UserDTO userDTO);
    TemporaryToken loginAdmin(User user);

}
