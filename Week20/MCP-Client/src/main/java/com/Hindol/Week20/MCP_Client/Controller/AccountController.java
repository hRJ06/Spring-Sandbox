package com.Hindol.Week20.MCP_Client.Controller;

import com.Hindol.Week20.MCP_Client.Entity.Step;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ai.chat.prompt.PromptTemplate;

import java.util.*;

import static com.Hindol.Week20.MCP_Client.Util.Constant.*;
import static com.Hindol.Week20.MCP_Client.Util.LLMUtil.*;

@RestController
public class AccountController {

    private final ChatClient chatClient;
    private final ChatClient openAIClient;

    public AccountController(@Qualifier("MCPChatClient") ChatClient chatClient,
                             @Qualifier("OpenAIChatClient") ChatClient openAIClient) {
        this.chatClient = chatClient;
        this.openAIClient = openAIClient;
    }

    @GetMapping("/account")
    public String getAccount(@RequestParam("q") String userPrompt) {
        String plannerPrompt = build(userPrompt);
        String planJson = openAIClient.prompt(plannerPrompt).call().content();

        List<Step> steps = parse(planJson);
        Map<String, List<String>> variables = new HashMap<>();
        StringBuilder finalResponse = new StringBuilder();

        for (Step step : steps) {
            if (Objects.nonNull(step.dependsOn)) {
                List<String> inputs = variables.get(step.dependsOn);
                if (Objects.isNull(inputs)) continue;

                for (String input : inputs) {
                    String filledPrompt = step.prompt.replace(VALUE_FIELD, input);
                    PromptTemplate pt = new PromptTemplate(filledPrompt);
                    String response = chatClient.prompt(pt.create()).call().content();
                    finalResponse
                            .append(STEP_PREFIX).append(step.step)
                            .append(STEP_FOR_INPUT).append(input).append(STEP_FOR_INPUT_SUFFIX)
                            .append(response);
                }
            } else {
                PromptTemplate pt = new PromptTemplate(step.prompt);
                String response = chatClient.prompt(pt.create()).call().content();
                finalResponse
                        .append(STEP_PREFIX).append(step.step).append(STEP_SUFFIX)
                        .append(response);

                if (Objects.nonNull(step.outputVar)) {
                    List<String> values = extract(response, step.outputVar);
                    variables.put(step.outputVar, values);
                }
            }
        }

        return finalResponse.toString();
    }
}
