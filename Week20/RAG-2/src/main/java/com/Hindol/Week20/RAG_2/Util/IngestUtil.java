package com.Hindol.Week20.RAG_2.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.Hindol.Week20.RAG_2.Util.Constant.*;

public class IngestUtil {

    private static final Logger log = LoggerFactory.getLogger(IngestUtil.class);

    public static void prepareContext(String response) throws IOException {
        Document data = Jsoup.parse(response);

        Element directoryContainer = data.getElementById(DIRECTORY_STRUCTURE);
        String directory = directoryContainer.text();

        Element fileContent = data.selectFirst(DIRECTORY_CONTENT);
        String escapedContent = fileContent.text();
        String content = Parser.unescapeEntities(escapedContent, false);

        log.info("CONTEXT PREPARED!");

        String context = directory + "\n\n" + content;
        storeContext(context);
    }

    private static void storeContext(String content) throws IOException {
        Path filePath = Paths.get(CONTEXT_PATH);
        Files.write(filePath, content.getBytes(StandardCharsets.UTF_8));
        log.info("CONTEXT STORED!");
    }
}
