package com.example.agent.mcp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@Component
public class McpHttpClient {

    private final WebClient web;
    private final String path;

    public McpHttpClient(WebClient web,
            @Value("${mcp.path:/mcp}") String path) {
        this.web = web;
        this.path = path;
    }

    /**
     * Calls a specific MCP tool via JSON-RPC.
     *
     * @param toolName  name of the MCP tool (e.g. "create_issue")
     * @param arguments map of arguments to pass to the tool
     * @return the "result" field of the JSON-RPC response
     */
    public Mono<Object> callTool(String toolName, Map<String, Object> arguments) {
        Map<String, Object> payload = Map.of(
                "jsonrpc", "2.0",
                "id", UUID.randomUUID().toString(),
                "method", "tools/call",
                "params", Map.of(
                        "name", toolName,
                        "arguments", arguments));

        return web.post()
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("MCP HTTP " + response.statusCode() + ": " + body)))
                .bodyToMono(Map.class)
                .map(resp -> {
                    if (resp.containsKey("error")) {
                        throw new RuntimeException("MCP error: " + resp.get("error"));
                    }
                    Object result = resp.get("result");
                    if (result == null) {
                        throw new RuntimeException("MCP missing result, full response: " + resp);
                    }
                    return result;
                });
    }

    /**
     * Lists all available MCP tools via JSON-RPC.
     *
     * @return the "result" field containing the list of tools
     */
    public Mono<Object> listTools() {
        Map<String, Object> payload = Map.of(
                "jsonrpc", "2.0",
                "id", UUID.randomUUID().toString(),
                "method", "tools/list",
                "params", Map.of());

        return web.post()
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("MCP HTTP " + response.statusCode() + ": " + body)))
                .bodyToMono(Map.class)
                .map(resp -> {
                    if (resp.containsKey("error")) {
                        throw new RuntimeException("MCP error: " + resp.get("error"));
                    }
                    return resp.get("result");
                });
    }
}
