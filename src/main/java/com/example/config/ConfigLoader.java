package com.example.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private final Dotenv dotenv;
    private final Properties properties;

    public ConfigLoader() {
        dotenv = Dotenv.configure().load();
        properties = new Properties();

        try (FileInputStream fis = new FileInputStream("src/main/resources/application.conf")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file.", e);
        }
    }

    public String getBotToken() {
        return dotenv.get("BOT_TOKEN");
    }

    public String getUserId() {
        return properties.getProperty("user_id");
    }

    public String getBotName() {
        return properties.getProperty("bot_name");
    }
}
