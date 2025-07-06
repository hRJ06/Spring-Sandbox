package com.Hindol.Week20.LangChain.Controller;

import com.Hindol.Week20.LangChain.Assistant.AIAssistant;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StockController {

    private final AIAssistant assistant;

    @GetMapping("/chat")
    public String chat(@RequestParam("q") String prompt) {
        return assistant.chat(prompt);
    }
}
