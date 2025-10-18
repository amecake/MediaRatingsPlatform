package org.example.handler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.User;
import org.example.persistence.IUserRepository;
import org.example.persistence.UserSqlRepository;
import org.example.service.IUserService;
import org.example.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Postman is sending a request
        // Check if request method is POST
        // Can only be POST, no GET for example
        if (!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        // Save Postman's request body
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes());

        // Print Postman's request body
        System.out.println("Received: " + body);

        // Create Jackson ObjectMapper
        // Make it so Jackson can access private fields
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // Serialize JSON to User object
        User newUser = mapper.readValue(body, User.class);
        System.out.println("Parsed user: \n" + newUser);


        // Now register user into repository
        IUserRepository repository = UserSqlRepository.getInstance();
        IUserService userService = new UserService();
        boolean uniqueUsername = userService.register(newUser);

        // Build response
        Map<String, String> responseObject = new HashMap<>();

        // Response depends on state
        if (uniqueUsername) {
            responseObject.put("message", "User " + newUser.getUsername() + " registered successfully");
            sendResponse(exchange, 201, responseObject, mapper);
        } else {
            responseObject.put("message", "Username already exists");
            sendResponse(exchange, 409, responseObject, mapper);
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode,
                              Map<String, String> responseObject, ObjectMapper mapper) throws IOException {
        String responseJson = mapper.writeValueAsString(responseObject);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, responseJson.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseJson.getBytes());
        }
    }
}
