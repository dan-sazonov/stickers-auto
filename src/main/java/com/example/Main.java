package com.example;

import com.example.config.ConfigLoader;
import com.example.telegram.TelegramApiClient;
import com.example.utils.FileValidator;

import java.io.Console;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        ConfigLoader config = new ConfigLoader();
        TelegramApiClient apiClient = new TelegramApiClient(config.getBotToken());

        Console console = System.console();
        if (console == null) {
            System.err.println("No console available");
            return;
        }

        String filePath;
        do {
            filePath = console.readLine("Enter path to sticker file: ");
            if (!FileValidator.isValidFile(filePath)) {
                console.printf("Invalid file path. Please enter again: %n");
            }
        } while (!FileValidator.isValidFile(filePath));

        String stickerPackName = console.readLine("Enter sticker pack name: ");
        String stickerPackTitle = console.readLine("Enter sticker pack title: ");

        File stickerFile = new File(filePath);
        try {
            apiClient.createStickerSet(config.getUserId(), stickerPackName, config.getBotName(), stickerPackTitle, "🎈", stickerFile);
        } catch (Exception e) {
            System.err.println("Error creating sticker pack: " + e.getMessage());
        }
    }
}
