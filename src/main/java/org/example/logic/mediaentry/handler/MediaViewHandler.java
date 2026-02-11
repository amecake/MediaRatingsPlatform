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
        boolean valid = verifyRequestMethod(exchange, "GET");
        if (!valid) return;

        User user = checkToken(exchange);
        if (user == null) {
            return;
        }

        System.out.println("User is authorized: " + user.getUsername());

        // Read InputStream
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes());
        // System.out.println("Received: " + body);

        // Use Jackson ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // Get ID from path
        Integer id = extractIdFromPath(exchange);
        if (id == null) {
            sendResponse(exchange, 400, Map.of("message", "Invalid ID"), mapper);
            return;
        }

        try {
            // Now put it into user's media entry array
            MediaEntry mediaEntry = mediaService.getMediaEntryByIndex(id);

            // Build response
            Map<String, String> responseObject = new HashMap<>();

            if (mediaEntry == null) {
                responseObject.put("message", "This ID is not valid");
                sendResponse(exchange, 409, responseObject, mapper);
            }

            sendResponse(exchange, 200, mediaEntry, mapper);
        } catch (RuntimeException e) {
            // If any SQL error occurs, return a server error
            sendResponse(exchange, 500, Map.of("message", "Database error"), mapper);
            return;
        }

    }
}
