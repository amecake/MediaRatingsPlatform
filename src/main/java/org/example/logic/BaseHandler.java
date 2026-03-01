package org.example.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import org.example.logic.user.model.User;

import java.io.IOException;
import java.io.OutputStream;

// Abstract in this case means i can't instantiate BaseHandler directly
public abstract class BaseHandler {
    protected boolean verifyRequestMethod(HttpExchange exchange, String requestMethod) throws IOException {
        if (!exchange.getRequestMethod().equals(requestMethod)) {
            exchange.sendResponseHeaders(405, -1);
            return false;
        }

        return true;
    }

    protected User checkToken(HttpExchange exchange) throws IOException {

        // Check if Authorization is sent correctly
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.sendResponseHeaders(401, -1); // unauthorized
            return null;
        }

        // Check if token is valid
        String token = authHeader.substring("Bearer ".length());
        if (!TokenService.isTokenValid(token)) {
            exchange.sendResponseHeaders(403, -1); // forbidden
            return null;
        }

        return TokenService.getUserByToken(token); // you can now access the logged-in user
    }

    protected Integer extractIdFromPath(HttpExchange exchange) {
        try {
            String path = exchange.getRequestURI().getPath();
            String[] parts = path.split("/");
            return Integer.parseInt(parts[parts.length - 1]);
        } catch (Exception e) {
            return null;
        }
    }

    protected void sendResponse(HttpExchange exchange, int statusCode,
                                Object responseObject, ObjectMapper mapper)
            throws IOException {
        String responseJson = mapper.writeValueAsString(responseObject);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, responseJson.getBytes().length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseJson.getBytes());
        }
    }

}
