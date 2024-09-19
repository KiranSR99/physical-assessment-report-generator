package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.config.FilePathProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FilePathProperties filePathProperties;

    public String saveFile(MultipartFile file) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        String basePath;

        if (os.contains("win")) {
            basePath = filePathProperties.getWindows();
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            basePath = filePathProperties.getLinux();
        } else {
            basePath = filePathProperties.getMac();
        }

        Path path = Paths.get(basePath + file.getOriginalFilename());
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        return file.getOriginalFilename();
    }
}