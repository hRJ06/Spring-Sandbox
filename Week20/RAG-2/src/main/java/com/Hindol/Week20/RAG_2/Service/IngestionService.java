package com.Hindol.Week20.RAG_2.Service;

import com.Hindol.Week20.RAG_2.Util.IngestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;

import static com.Hindol.Week20.RAG_2.Util.Constant.*;


@Service
public class IngestionService {

    @Autowired
    private ApplicationContext ctx;

    private final RestTemplate restTemplate;
    private final VectorStore vectorStore;

    private final Logger log = LoggerFactory.getLogger(IngestionService.class);

    public IngestionService(RestTemplate restTemplate, VectorStore vectorStore) {
        this.restTemplate = restTemplate;
        this.vectorStore = vectorStore;
    }

    public void ingest(String url) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add(INPUT_TEXT, url);
        map.add(MAX_FILE_SIZE, "500");
        map.add(PATTERN_TYPE, "exclude");
        map.add(TOKEN, "");
        map.add(PATTERN, "");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("https://gitingest.com", request , String.class );
        String content = response.getBody();

        IngestUtil.prepareContext(content);
        Resource resource = ctx.getResource("classpath:/Context.txt");

        var reader = new TextReader(resource);
        TextSplitter textSplitter = new TokenTextSplitter();
        vectorStore.accept(textSplitter.apply(reader.get()));
        log.info("VECTOR STORE LOADED WITH DATA!");
    }
}
