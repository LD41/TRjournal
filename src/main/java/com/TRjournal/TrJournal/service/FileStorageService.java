package com.TRjournal.TrJournal.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    private final Path root;

    /**
     * Initializes the FileStorageService with the specified upload directory.
     * Creates the directory if it doesn't exist.
     */
    public FileStorageService(@Value("${file.upload-dir:uploads}") String uploadDir) {
        this.root = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    /**
     * Stores a file in the upload directory with a unique filename.
     * Returns the generated filename.
     */
    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Failed to store empty file");
        }
        String filename = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename(), "unnamed"));
        try {
            if (filename.contains("..")) {
                throw new SecurityException("Cannot store file with relative path outside current directory " + filename);
            }
            Files.copy(file.getInputStream(), this.root.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
    }

    /**
     * Retrieves the Path for a given filename in the upload directory.
     */
    public Path load(String filename) {
        return root.resolve(filename);
    }
}
