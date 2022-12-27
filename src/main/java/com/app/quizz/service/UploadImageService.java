package com.app.quizz.service;

import com.app.quizz.exception.BusinessException;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
public class UploadImageService {
    private static final String DONWLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/quiz-image-storage.appspot.com/o/%s?alt=media";

    // 1
    public String imageConverter(MultipartFile multipartFile) {
        try {
            String fileName = getExtenions(multipartFile.getOriginalFilename());
            File file = convertToFile(multipartFile, fileName);
            var RESPONSE_URL = uploadFile(file, fileName);
            file.delete();
            return RESPONSE_URL;
        } catch (Exception e) {
            e.getStackTrace();
            throw new BusinessException("Error upload file");
        }
    }

    // 2
    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return file;
    }

    // 3
    private String uploadFile(File file, String fileName) throws IOException {
        BlobId blobId = BlobId.of("quiz-image-storage.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("./src/main/resources/serviceAccountKey.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
        return String.format(DONWLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }

    // 4
    private String getExtenions(String fileName) {
        return fileName.split("\\.")[0];
    }
}
