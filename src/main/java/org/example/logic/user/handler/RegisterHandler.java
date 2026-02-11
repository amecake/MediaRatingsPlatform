package org.example.logic.user.handler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.logic.BaseHandler;
import org.example.logic.user.model.User;
import org.example.logic.user.service.IUserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RegisterHandler extends BaseHandler implements HttpHandler {
    private final IUserService userService;

    public RegisterHandler(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Verify correct request method
        boolean valid = verifyRequestMethod(exchange, "POST");
        if (!valid) return;

        // Read InputStream
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes());
        //System.out.println("Received: " + body);

        // Use Jackson ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // Serialize JSON into object
        User newUser = mapper.readValue(body, User.class);

        // Debugging
        //System.out.println("Parsed user: \n" + newUser);

        // Now register user into repository
        try {
            boolean success = userService.register(newUser);

            // Build response
            Map<String, String> responseObject = new HashMap<>();

            // Response depends on state
            if (success) {
                responseObject.put("message", "User " + newUser.getUsername() + " registered successfully");
                sendResponse(exchange, 201, responseObject, mapper);
            } else {
                responseObject.put("message", "Username already exists");
                sendResponse(exchange, 409, responseObject, mapper);
            }
        } catch (RuntimeException e) {
            // If any SQL error occurs, return a server error
            sendResponse(exchange, 500, Map.of("message", "Database error"), mapper);
        }
    }
}
