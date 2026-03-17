package com.example.agent.config;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("ci")
public class CiChatModelConfig {

    @Bean
    public ChatLanguageModel ciChatModel() {
        return new ChatLanguageModel() {
            @Override
            public String generate(String message) {
                return "CI OK";
            }

            @Override
            public Response<AiMessage> generate(List<ChatMessage> messages) {
                return Response.from(AiMessage.from("CI OK"));
            }
        };
    }
}
