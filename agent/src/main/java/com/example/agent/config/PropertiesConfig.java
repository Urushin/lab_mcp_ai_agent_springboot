package com.example.agent.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfig {

    @ConfigurationProperties(prefix = "github")
    public record GithubProperties(String owner, String repo) {
    }

    @ConfigurationProperties(prefix = "gemini")
    public record GeminiProperties(
            String apiKey,
            String model,
            Integer maxTokens,
            Integer timeoutSeconds) {
    }

    @ConfigurationProperties(prefix = "mcp")
    public record McpProperties(String baseUrl, String path) {
    }
}
