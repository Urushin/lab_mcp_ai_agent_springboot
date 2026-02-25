package com.example.agent;

import com.example.agent.agent.BacklogAgent;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class AgentApplicationTests {

	@MockBean
	GoogleAiGeminiChatModel geminiChatModel;

	@MockBean
	BacklogAgent backlogAgent;

	@Test
	void contextLoads() {
	}
}
