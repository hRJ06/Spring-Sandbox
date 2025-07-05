package com.Hindol.Week20.MCP_Client.Configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {

    @Bean
    @Qualifier("MCPChatClient")
    public ChatClient ollamaChatClient(@Qualifier("ollamaChatModel") ChatModel model,
                                       ToolCallbackProvider toolCallbackProvider) {
        return ChatClient.builder(model)
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
    }

    @Bean
    @Qualifier("OpenAIChatClient")
    public ChatClient openAiChatClient(@Qualifier("openAiChatModel") ChatModel model) {
        return ChatClient.builder(model).build();
    }
}
