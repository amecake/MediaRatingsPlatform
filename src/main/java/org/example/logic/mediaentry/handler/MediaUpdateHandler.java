package org.example.logic.mediaentry.handler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.logic.BaseHandler;
import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.mediaentry.model.MediaType;
import org.example.logic.mediaentry.service.IMediaService;
import org.example.logic.user.model.User;
import org.example.logic.user.service.IUserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaUpdateHandler extends BaseHandler implements HttpHandler {
    private final IMediaService mediaService;

    public MediaUpdateHandler(IMediaService mediaService) {
        this.mediaService = mediaService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Verify correct request method
        boolean valid = verifyRequestMethod(exchange, "PUT");
        if (!valid) return;

        // Verify token
        User loggedInUser = checkToken(exchange);
        if (loggedInUser == null) {
            return;
        }

        // Debugging
        //System.out.println("User is authorized: " + loggedInUser.getUsername());

        // Read InputStream
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes());
        // System.out.println("Received: " + body);

        // Use Jackson ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // Get ID from path
        String path = exchange.getRequestURI().getPath(); // zB. /media/update/0
        String[] parts = path.split("/");
        int id;

        // Check if it's an int
        try {
            id = Integer.parseInt(parts[parts.length - 1]);
        } catch (NumberFormatException e) {
            sendResponse(exchange, 400, Map.of("message", "Not an integer"), mapper);
            return;
        }

        //System.out.println("ID: " + id);

        // find media entry that needs to be changed
        MediaEntry mediaToUpdate = mediaService.getMediaEntryByIndex(id);

        if (mediaToUpdate == null) {
            sendResponse(exchange, 404, Map.of("message", "Media not found"), mapper);
            return;
        }

        System.out.println("Media to update will be: " + mediaToUpdate);

        JsonNode jsonNode = mapper.readTree(body);
        if (jsonNode.has("title")) {
            mediaToUpdate.setTitle(jsonNode.get("title").asText());
        }
        if (jsonNode.has("description")) {
            mediaToUpdate.setDescription(jsonNode.get("description").asText());
        }
        if (jsonNode.has("type")) {
            mediaToUpdate.setType(MediaType.valueOf(jsonNode.get("type").asText().toUpperCase()));
        }
        if (jsonNode.has("releaseYear")) {
            mediaToUpdate.setReleaseYear(jsonNode.get("releaseYear").asInt());
        }
        if (jsonNode.has("genre")) {
            mediaToUpdate.setGenre(jsonNode.get("genre").asText());
        }
        if (jsonNode.has("ageRestriction")) {
            mediaToUpdate.setAgeRestriction(jsonNode.get("ageRestriction").asBoolean());
        }

        System.out.println("Updated media will be: " + mediaToUpdate);

        String responseMessage = mediaService.updateMediaEntry(loggedInUser, mediaToUpdate);

        // Build response
        Map<String, String> responseObject = new HashMap<>();

        // Response depends on state
        if (responseMessage.equals("success")) {
            responseObject.put("message", "Media entry updated successfully");
            sendResponse(exchange, 201, responseObject, mapper);
        } else {
            responseObject.put("message", "An error has occurred");
            sendResponse(exchange, 409, responseObject, mapper);
        }
    }
}
