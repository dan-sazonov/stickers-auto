package com.example.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileValidator {
    public static boolean isValidDirectory(String path) {
        File directory = new File(path);
        return directory.exists() && directory.isDirectory();
    }

    public static boolean isValidImageFile(File file) {
        String[] validExtensions = {".png", ".jpg", ".jpeg"};
        return file.isFile() && Arrays.stream(validExtensions).anyMatch(file.getName().toLowerCase()::endsWith)
                && !EmojiExtractor.extractEmojisFromFileName(file.getName()).isEmpty();
    }

    public static List<File> getValidImageFiles(String directory) {
        List<File> validFiles = new ArrayList<>();
        File dir = new File(directory);

        if (isValidDirectory(directory)) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (isValidImageFile(file)) {
                        validFiles.add(file);
                    }
                }
            }
        }
        return validFiles;
    }
}
