package ru.lanit.service.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    void saveImage(MultipartFile imageFile) throws IOException;
}
