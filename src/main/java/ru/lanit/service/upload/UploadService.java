package ru.lanit.service.upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    void saveImage(MultipartFile imageFile) throws IOException;
}
