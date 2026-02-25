package com.example.agent.web;

import com.example.agent.agent.BacklogAgent;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AgentControllerIT {

    @MockBean
    GoogleAiGeminiChatModel geminiChatModel;

    @MockBean
    BacklogAgent backlogAgent;

    @Autowired
    WebTestClient web;

    @Test
    void should_return_agent_response_for_valid_prompt() {
        // Arrange: stub the agent to return a predictable response
        when(backlogAgent.handle(anyString()))
                .thenReturn("Issue created: #42 - Add OpenTelemetry support");

        // Act + Assert
        web.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/run")
                        .queryParam("prompt", "Create a task to add OpenTelemetry")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Issue created: #42 - Add OpenTelemetry support");
    }

    @Test
    void should_return_400_when_prompt_is_missing() {
        // Act + Assert: no prompt param → Spring's @RequestParam fails → 400
        web.post()
                .uri("/api/run")
                .exchange()
                .expectStatus().isBadRequest();
    }
}
