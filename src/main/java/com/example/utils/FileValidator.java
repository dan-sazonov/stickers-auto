package com.example.utils;

import java.io.File;
import java.util.Arrays;

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
}
