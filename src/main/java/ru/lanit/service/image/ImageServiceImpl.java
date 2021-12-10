package ru.lanit.service.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl implements ImageService {

//    @Autowired
//    ServletContext context;

    private final ServletContext context;

    @Autowired
    public ImageServiceImpl(ServletContext context) {
        this.context = context;
    }

    @Override
    public void saveImage(MultipartFile imageFile) throws IOException {

        String folder = context.getRealPath("") + "/WEB-INF/resources/upload/";
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(folder + imageFile.getOriginalFilename());

        String pathString = path.toString();
        System.out.println(pathString);

        Files.write(path, bytes);
    }
}
