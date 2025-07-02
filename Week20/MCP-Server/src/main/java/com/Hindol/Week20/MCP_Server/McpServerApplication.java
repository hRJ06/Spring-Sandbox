package com.Hindol.Week20.MCP_Server;

import com.Hindol.Week20.MCP_Server.tools.SellerAccountTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpServerApplication.class, args);
	}

	@Bean
	public ToolCallbackProvider tools(SellerAccountTools sellerAccountTool) {
		return MethodToolCallbackProvider.builder()
				.toolObjects(sellerAccountTool)
				.build();
	}
}
