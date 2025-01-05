package com.example;

import com.example.config.ConfigLoader;
import com.example.telegram.TelegramApiClient;
import com.example.utils.EmojiExtractor;
import com.example.utils.FileValidator;

import java.io.Console;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        ConfigLoader config = new ConfigLoader();
        TelegramApiClient apiClient = new TelegramApiClient(config.getBotToken());

        Console console = System.console();
        if (console == null) {
            System.err.println("No console available");
            return;
        }

        String directoryPath;
        do {
            directoryPath = console.readLine("Enter path to the directory containing stickers: ");
            if (!FileValidator.isValidDirectory(directoryPath)) {
                console.printf("Invalid directory path. Please enter again: %n");
            }
        } while (!FileValidator.isValidDirectory(directoryPath));

        String stickerPackName = console.readLine("Enter sticker pack name: ");
        String stickerPackTitle = console.readLine("Enter sticker pack title: ");

        File directory = new File(directoryPath);
        List<File> stickerFiles = Arrays.asList(Objects.requireNonNull(directory.listFiles(FileValidator::isValidImageFile)));
        if (stickerFiles.isEmpty()) {
            System.err.println("No valid image files found in the directory.");
            return;
        }

        try {
            boolean stickerPackCreated = false;

            for (File sticker : stickerFiles) {
                String emojis = EmojiExtractor.extractEmojisFromFileName(sticker.getName());
                if (emojis.isEmpty()) {
                    System.out.println("Skipping file without emojis: " + sticker.getName());
                    continue;
                }

                if (!stickerPackCreated) {
                    apiClient.createStickerSet(
                            config.getUserId(),
                            stickerPackName,
                            config.getBotName(),
                            stickerPackTitle,
                            emojis,
                            sticker
                    );
                    stickerPackCreated = true;
                } else {
                    apiClient.addStickerToSet(config.getUserId(), stickerPackName, config.getBotName(), emojis, sticker);
                }
            }

            if (!stickerPackCreated) {
                System.err.println("No valid stickers with emojis found. Sticker pack was not created.");
            }
        } catch (Exception e) {
            System.err.println("Error processing stickers: " + e.getMessage());
        }
    }
}
