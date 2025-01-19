package com.example.telegram;

import com.vdurmont.emoji.EmojiParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

public class TelegramApiClient {
    private final String botToken;

    public TelegramApiClient(String botToken) {
        this.botToken = botToken;
    }

    public void createStickerSet(String userId, String name, String botName, String title, String emoji, File stickerFile) throws Exception {
        String url = "https://api.telegram.org/bot" + botToken + "/createNewStickerSet";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("user_id", userId, org.apache.http.entity.ContentType.create("text/plain", StandardCharsets.UTF_8));
            builder.addTextBody("name", (name + "_by_" + botName), org.apache.http.entity.ContentType.create("text/plain", StandardCharsets.UTF_8));
            builder.addTextBody("title", title, org.apache.http.entity.ContentType.create("text/plain", StandardCharsets.UTF_8));
            builder.addTextBody("emojis", emoji, org.apache.http.entity.ContentType.create("text/plain", StandardCharsets.UTF_8));
            builder.addBinaryBody("png_sticker", new FileInputStream(stickerFile), org.apache.http.entity.ContentType.create("image/png"), stickerFile.getName());

            HttpEntity entity = builder.build();
            post.setEntity(entity);

            HttpResponse response = client.execute(post);
            String result = EntityUtils.toString(response.getEntity());
            if (!result.contains("\"ok\":true")) {
                throw new Exception("Failed to create sticker set: " + result);
            }

            String emojiName = EmojiParser.parseToAliases(emoji);
            System.out.println("Sticker pack created successfully with the first sticker: " + emojiName);
        }
    }

    public void addStickerToSet(String userId, String name, String botName, String emoji, File stickerFile) throws Exception {
        String url = "https://api.telegram.org/bot" + botToken + "/addStickerToSet";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("user_id", userId, org.apache.http.entity.ContentType.create("text/plain", StandardCharsets.UTF_8));
            builder.addTextBody("name", (name + "_by_" + botName), org.apache.http.entity.ContentType.create("text/plain", StandardCharsets.UTF_8));
            builder.addTextBody("emojis", emoji, org.apache.http.entity.ContentType.create("text/plain", StandardCharsets.UTF_8));
            builder.addBinaryBody("png_sticker", new FileInputStream(stickerFile), org.apache.http.entity.ContentType.create("image/png"), stickerFile.getName());

            HttpEntity entity = builder.build();
            post.setEntity(entity);

            HttpResponse response = client.execute(post);
            String result = EntityUtils.toString(response.getEntity());

            if (!result.contains("\"ok\":true")) {
                System.out.println("Failed to add sticker: " + EmojiParser.parseToAliases(emoji));
                return;
            }

            String emojiName = EmojiParser.parseToAliases(emoji);
            System.out.println("Sticker added successfully: " + emojiName);
        }
    }
}
