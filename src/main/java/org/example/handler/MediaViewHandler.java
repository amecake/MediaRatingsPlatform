package org.example.handler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.MediaEntry;
import org.example.model.User;
import org.example.persistence.IUserRepository;
import org.example.persistence.UserSqlRepository;
import org.example.service.IUserService;
import org.example.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MediaViewHandler extends BaseHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Check if Postman sends POST
        // But it still needs to verify if the session is valid (like in php)
        if (!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        User user = checkToken(exchange);
        if (user == null) {
            return;
        }

        System.out.println("User is authorized: " + user.getUsername());

        // Save Postman's request body
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes());

        // Print Postman's request body
        // System.out.println("Received: " + body);

        // Create Jackson ObjectMapper
        // Make it so Jackson can access private fields
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // Serialize JSON to index
        JsonNode jsonNode = mapper.readTree(body);
        int index = jsonNode.get("index").asInt();
        System.out.println("Parsed Index: " + index);

        // Now put it into user's media entry array
        IUserRepository repository = UserSqlRepository.getInstance();
        IUserService userService = new UserService();
        String responseMessage = userService.viewMediaEntry(user, index);

        // Build response
        Map<String, String> responseObject = new HashMap<>();

        // Response depends on state
        if (responseMessage.equals("success")) {
            // Generate token on success
            String token = TokenService.generateToken(user);
            responseObject.put("message", "Media entry listed successfully");
            sendResponse(exchange, 201, responseObject, mapper);
        } else {
            responseObject.put("message", "An error has occurred");
            sendResponse(exchange, 409, responseObject, mapper);
        }
    }
}
