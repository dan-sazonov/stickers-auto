package com.example.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmojiExtractor {

    private static final Pattern EMOJI_PATTERN = Pattern.compile(
            "[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]|[\\u2600-\\u26FF\\u2700-\\u27BF]|\\uFE0F"
    );

    public static String extractEmojisFromFileName(String fileName) {
        Matcher matcher = EMOJI_PATTERN.matcher(fileName);
        StringBuilder emojis = new StringBuilder();

        while (matcher.find()) {
            emojis.append(matcher.group()).append(" ");
        }

        return emojis.toString().trim();
    }
}
