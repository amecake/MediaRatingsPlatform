package org.example.logic.mediaentry.handler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.logic.BaseHandler;
import org.example.logic.mediaentry.service.IMediaService;
import org.example.logic.user.model.User;
import org.example.logic.user.service.IUserService;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MediaViewHandler extends BaseHandler implements HttpHandler {
    private final IMediaService mediaService;

    public MediaViewHandler(IMediaService mediaService) {
        this.mediaService = mediaService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Check if request method is POST
        boolean valid = verifyRequestMethod(exchange, "POST");
        if (!valid) return;

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
        String responseMessage = mediaService.viewMediaEntry(user, index);

        // Build response
        Map<String, String> responseObject = new HashMap<>();

        // Response depends on state
        if (responseMessage.equals("success")) {
            responseObject.put("message", "Media entry listed successfully");
            sendResponse(exchange, 201, responseObject, mapper);
        } else {
            responseObject.put("message", "An error has occurred");
            sendResponse(exchange, 409, responseObject, mapper);
        }
    }
}
