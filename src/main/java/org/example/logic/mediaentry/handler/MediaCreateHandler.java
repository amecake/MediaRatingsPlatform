package org.example.logic.mediaentry.handler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.logic.BaseHandler;
import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.mediaentry.service.IMediaService;
import org.example.logic.user.model.User;
import org.example.logic.user.service.IUserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MediaCreateHandler extends BaseHandler implements HttpHandler {
    private final IMediaService mediaService;

    public MediaCreateHandler(IMediaService mediaService) {
        this.mediaService = mediaService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Verify correct request method
        boolean valid = verifyRequestMethod(exchange, "POST");
        if (!valid) return;

        // Verify token
        User user = checkToken(exchange);
        if (user == null) {
            return;
        }
        //System.out.println("Authenticated user ID: " + user.getId() + ", username: " + user.getUsername());

        // Debugging
        //System.out.println("User is authorized: " + user.getUsername());

        // Read InputStream
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes());
        //System.out.println("Received: " + body);

        // Use Jackson ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // Deserialize JSON into object
        MediaEntry mediaEntry = mapper.readValue(body, MediaEntry.class);
        System.out.println("Parsed MediaEntry: " + mediaEntry);

        try {
            // Now send media entry object to mediaService
            String responseMessage = mediaService.createMediaEntry(user, mediaEntry);

            // Build response
            Map<String, String> responseObject = new HashMap<>();

            // Response depends on state
            if (responseMessage.equals("success")) {
                responseObject.put("message", "Media entry created successfully");
                sendResponse(exchange, 201, responseObject, mapper);
            } else {
                responseObject.put("message", "An error has occurred");
                sendResponse(exchange, 409, responseObject, mapper);
            }
        } catch (Exception e) {
            // Catch unexpected exceptions
            Map<String, String> responseObject = new HashMap<>();
            responseObject.put("message", "Server error: " + e.getMessage());
            sendResponse(exchange, 500, responseObject, mapper);
        }
    }
}
