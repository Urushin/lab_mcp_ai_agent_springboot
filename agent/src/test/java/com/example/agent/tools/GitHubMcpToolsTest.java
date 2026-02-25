package com.example.agent.tools;

import com.example.agent.mcp.McpHttpClient;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GitHubMcpToolsTest {

    @Test
    void should_call_mcp_create_issue_with_correct_params() {
        // Arrange
        McpHttpClient mcp = mock(McpHttpClient.class);
        when(mcp.callTool(eq("create_issue"), anyMap()))
                .thenReturn(Mono.just(Map.of(
                        "number", 42,
                        "html_url", "https://github.com/owner/repo/issues/42")));

        GitHubMcpTools tools = new GitHubMcpTools(mcp, "owner", "repo");

        // Act
        String result = tools.createIssue("Add OpenTelemetry support", "Export traces via OTLP");

        // Assert
        assertThat(result).startsWith("Issue created successfully:");
        verify(mcp, times(1)).callTool(eq("create_issue"), anyMap());
    }

    @Test
    void should_pass_correct_arguments_to_mcp() {
        // Arrange
        McpHttpClient mcp = mock(McpHttpClient.class);
        when(mcp.callTool(anyString(), anyMap()))
                .thenReturn(Mono.just("created"));

        GitHubMcpTools tools = new GitHubMcpTools(mcp, "my-org", "my-repo");

        // Act
        tools.createIssue("My Title", "My Body");

        // Assert: verify the exact arguments passed to callTool
        verify(mcp).callTool(eq("create_issue"), argThat(args -> {
            Map<?, ?> m = (Map<?, ?>) args;
            return "my-org".equals(m.get("owner"))
                    && "my-repo".equals(m.get("repo"))
                    && "My Title".equals(m.get("title"))
                    && "My Body".equals(m.get("body"));
        }));
    }
}
