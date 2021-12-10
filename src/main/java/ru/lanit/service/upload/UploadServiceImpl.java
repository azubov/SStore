package ru.lanit.service.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.lanit.model.ImageSet;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadServiceImpl implements UploadService {

    public static final String UPLOAD_DIRECTORY = "/WEB-INF/resources/upload/";
    private final ServletContext context;

    @Autowired
    public UploadServiceImpl(ServletContext context) {
        this.context = context;
    }

    @Override
    public void saveImage(MultipartFile imageFile) throws IOException {

        String projectRootPath = context.getRealPath(".");
        String uploadFolder = projectRootPath + UPLOAD_DIRECTORY;

        Path path = Paths.get(uploadFolder + imageFile.getOriginalFilename());

        byte[] bytes = imageFile.getBytes();
        Files.write(path, bytes);

        ImageSet.addImage(imageFile.getOriginalFilename());
    }
}
