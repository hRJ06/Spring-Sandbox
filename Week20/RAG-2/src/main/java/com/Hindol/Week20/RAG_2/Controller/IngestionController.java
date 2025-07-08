package com.Hindol.Week20.RAG_2.Controller;

import com.Hindol.Week20.RAG_2.Service.IngestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class IngestionController {
    private final IngestionService ingestionService;

    public IngestionController(IngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }
    @GetMapping("/ingest")
    public void ingest(@RequestParam("url") String url) throws IOException {
        ingestionService.ingest(url);
    }
}
