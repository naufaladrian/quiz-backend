package com.app.quizz.service;

import com.app.quizz.model.Materi;
import com.app.quizz.repository.MateriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriServiceImpl implements MateriService{
    @Autowired
    private MateriRepository materiRepository;
    @Override
    public List<Materi> getAllMateri() {
        return materiRepository.findAll();
    }
}
