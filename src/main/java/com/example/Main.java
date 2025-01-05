package com.example;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;



public class Main {
    public static void main(String[] args) {
        String token = "";
        String url = "https://api.telegram.org/bot" + token + "/createNewStickerSet";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("user_id", "385056286", org.apache.http.entity.ContentType.create("text/plain", StandardCharsets.UTF_8));
            builder.addTextBody("name", "fizicatest2_by_stickers_nw_bot", org.apache.http.entity.ContentType.create("text/plain", StandardCharsets.UTF_8));
            builder.addTextBody("title", "TestStickerPack", org.apache.http.entity.ContentType.create("text/plain", StandardCharsets.UTF_8));
            builder.addTextBody("emojis", "🎈", org.apache.http.entity.ContentType.create("text/plain", StandardCharsets.UTF_8));

            InputStream inputStream = Main.class.getResourceAsStream("/test.png");
            if (inputStream == null) {
                throw new RuntimeException("File not found: test.png");
            }
            builder.addBinaryBody("png_sticker", inputStream, org.apache.http.entity.ContentType.create("image/png"), "test.png");

            HttpEntity entity = builder.build();
            post.setEntity(entity);

            HttpResponse response = client.execute(post);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                String result = EntityUtils.toString(responseEntity);
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
