package com.app.quizz.controller;

import com.app.quizz.response.CommonResponse;
import com.app.quizz.response.CommonResponseGenerator;
import com.app.quizz.service.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/image")
@RestController
public class ImageController {
    @Autowired
    private UploadImageService uploadImageService;
    @PostMapping
    public CommonResponse<String> imageToLink(@RequestPart("file") MultipartFile img){
        return CommonResponseGenerator.successResponse(uploadImageService.imageConverter(img));
    }
}
