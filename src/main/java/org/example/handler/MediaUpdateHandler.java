package org.example.handler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.MediaEntry;
import org.example.model.MediaType;
import org.example.model.User;
import org.example.persistence.IUserRepository;
import org.example.persistence.UserSqlRepository;
import org.example.service.IUserService;
import org.example.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MediaUpdateHandler extends BaseHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Check if Postman sends PUT
        // But it still needs to verify if the session is valid (like in php)
        if (!exchange.getRequestMethod().equals("PUT")) {
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

        // Get ID from path
        String path = exchange.getRequestURI().getPath(); // zB. /media/update/0
        String[] parts = path.split("/");
        int index = Integer.parseInt(parts[parts.length - 1]);
        System.out.println("Index: " + index);

        // Now put it into user's media entry array
        IUserRepository repository = UserSqlRepository.getInstance();
        IUserService userService = new UserService();

        // Serialize JSON to User object
        // find media entry that needs to be changed
        MediaEntry updatedMedia = userService.getMediaEntryByIndex(user, index);

        System.out.println("Media to update will be: " + updatedMedia);

        JsonNode jsonNode = mapper.readTree(body);
        if (jsonNode.has("title")) {
            updatedMedia.setTitle(jsonNode.get("title").asText());
        }
        if (jsonNode.has("description")) {
            updatedMedia.setDescription(jsonNode.get("description").asText());
        }
        if (jsonNode.has("type")) {
            updatedMedia.setType(MediaType.valueOf(jsonNode.get("type").asText().toUpperCase()));
        }
        if (jsonNode.has("releaseYear")) {
            updatedMedia.setReleaseYear(jsonNode.get("releaseYear").asInt());
        }
        if (jsonNode.has("genres")) {
            List<String> newGenres = new ArrayList<>();
            for (JsonNode genreNode : jsonNode.get("genres")) {
                newGenres.add(genreNode.asText());
            }
            updatedMedia.setGenres(newGenres);
        }
        if (jsonNode.has("ageRestriction")) {
            updatedMedia.setAgeRestriction(jsonNode.get("ageRestriction").asBoolean());
        }

        System.out.println("Updated media will be: " + updatedMedia);

        String responseMessage = userService.updateMediaEntry(user, updatedMedia, index);

        // Build response
        Map<String, String> responseObject = new HashMap<>();

        // Response depends on state
        if (responseMessage.equals("success")) {
            // Generate token on success
            String token = TokenService.generateToken(user);
            responseObject.put("message", "Media entry updated successfully");
            sendResponse(exchange, 201, responseObject, mapper);
        } else {
            responseObject.put("message", "An error has occurred");
            sendResponse(exchange, 409, responseObject, mapper);
        }
    }
}
