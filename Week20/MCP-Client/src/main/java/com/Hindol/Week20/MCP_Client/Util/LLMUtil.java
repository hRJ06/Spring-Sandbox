package com.Hindol.Week20.MCP_Client.Util;

import com.Hindol.Week20.MCP_Client.Entity.Step;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.Hindol.Week20.MCP_Client.Util.Constant.PROMPT_FILE_PATH;

public class LLMUtil {
    public static List<Step> parse(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Arrays.asList(mapper.readValue(json, Step[].class));
        } catch (Exception e) {
            throw new RuntimeException("INVALID STEP PLAN FROM OpenAI: " + json, e);
        }
    }

    public static List<String> extract(String response, String key) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("name\\s*[:=]\\s*['\"]?(\\w+)['\"]?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(response);
        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        return result;
    }

    public static String build(String userInstruction) {
        try {
            String basePrompt = new String(Files.readAllBytes(Paths.get(PROMPT_FILE_PATH)));
            return basePrompt + userInstruction;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file -  " + PROMPT_FILE_PATH, e);
        }
    }
}
