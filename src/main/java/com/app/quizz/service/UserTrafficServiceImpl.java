package com.app.quizz.service;

import com.app.quizz.model.UserTraffic;
import com.app.quizz.repository.UserTrafficRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTrafficServiceImpl implements UserTrafficService{

    @Autowired
    private UserTrafficRepository userTrafficRepository;

    @Override
    public List<UserTraffic> getAllUserTraffic() {
        return userTrafficRepository.findAll();
    }
}
