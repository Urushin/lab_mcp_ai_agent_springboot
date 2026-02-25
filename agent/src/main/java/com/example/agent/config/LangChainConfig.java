package com.example.agent.config;

import com.example.agent.agent.BacklogAgent;
import com.example.agent.tools.AgentTool;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LangChainConfig {

    @Bean
    public GoogleAiGeminiChatModel geminiChatModel(
            @Value("${gemini.api-key}") String apiKey,
            @Value("${gemini.model}") String model,
            @Value("${gemini.max-tokens:800}") Integer maxOutputTokens) {
        return GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName(model)
                .maxOutputTokens(maxOutputTokens)
                .build();
    }

    @Bean
    public BacklogAgent backlogAgent(GoogleAiGeminiChatModel model, List<AgentTool> tools) {
        System.out.println("=== Agent tools loaded: " + tools.size() + " ===");
        tools.forEach(t -> System.out.println(" - " + t.getClass().getName()));

        return AiServices.builder(BacklogAgent.class)
                .chatModel(model)
                .tools(tools.toArray())
                .build();
    }
}
