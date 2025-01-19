package com.example;

import com.example.config.ConfigLoader;
import com.example.telegram.TelegramApiClient;
import com.example.utils.FileValidator;
import com.example.utils.EmojiExtractor;

import java.io.Console;
import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ConfigLoader config = new ConfigLoader();
        TelegramApiClient apiClient = new TelegramApiClient(config.getBotToken());

        Console console = System.console();
        if (console == null) {
            System.err.println("No console available");
            return;
        }

        String mode = console.readLine("Select mode: [1] Create sticker pack, [2] Update sticker pack: ");
        if ("1".equals(mode)) {
            handleCreateStickerPack(console, apiClient, config);
        } else if ("2".equals(mode)) {
            handleUpdateStickerPack(console, apiClient, config);
        } else {
            System.out.println("Invalid mode selected.");
        }
    }

    private static void handleCreateStickerPack(Console console, TelegramApiClient apiClient, ConfigLoader config) {
        String dirPath;
        do {
            dirPath = console.readLine("Enter path to directory with stickers: ");
            if (!FileValidator.isValidDirectory(dirPath)) {
                console.printf("Invalid directory path. Please enter again.%n");
            }
        } while (!FileValidator.isValidDirectory(dirPath));

        String stickerPackName = console.readLine("Enter sticker pack name: ");
        String stickerPackTitle = console.readLine("Enter sticker pack title: ");

        List<File> validFiles = FileValidator.getValidImageFiles(dirPath);
        if (validFiles.isEmpty()) {
            System.out.println("No valid sticker files found in the directory.");
            return;
        }

        try {
            apiClient.createStickerSet(config.getUserId(), stickerPackName, config.getBotName(), stickerPackTitle,
                    EmojiExtractor.extractEmojisFromFileName(validFiles.get(0).getName()), validFiles.get(0));

            for (int i = 1; i < validFiles.size(); i++) {
                File file = validFiles.get(i);
                String emojis = EmojiExtractor.extractEmojisFromFileName(file.getName());
                apiClient.addStickerToSet(config.getUserId(), stickerPackName, config.getBotName(), emojis, file);
            }
            System.out.println("Sticker pack created successfully!");
        } catch (Exception e) {
            System.err.println("Error creating sticker pack: " + e.getMessage());
        }
    }

    private static void handleUpdateStickerPack(Console console, TelegramApiClient apiClient, ConfigLoader config) {
        String dirPath;
        do {
            dirPath = console.readLine("Enter path to directory with stickers: ");
            if (!FileValidator.isValidDirectory(dirPath)) {
                console.printf("Invalid directory path. Please enter again.%n");
            }
        } while (!FileValidator.isValidDirectory(dirPath));

        String stickerPackName = console.readLine("Enter sticker pack name to update: ");

        List<File> validFiles = FileValidator.getValidImageFiles(dirPath);
        if (validFiles.isEmpty()) {
            System.out.println("No valid sticker files found in the directory.");
            return;
        }

        try {
            for (File file : validFiles) {
                String emojis = EmojiExtractor.extractEmojisFromFileName(file.getName());
                apiClient.addStickerToSet(config.getUserId(), stickerPackName, config.getBotName(), emojis, file);
            }
            System.out.println("Sticker pack updated successfully!");
        } catch (Exception e) {
            System.err.println("Error updating sticker pack: " + e.getMessage());
        }
    }
}
