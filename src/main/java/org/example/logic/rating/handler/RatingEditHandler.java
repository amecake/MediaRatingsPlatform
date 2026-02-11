package org.example.logic.rating.handler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.logic.BaseHandler;
import org.example.logic.mediaentry.model.MediaType;
import org.example.logic.rating.model.Rating;
import org.example.logic.rating.service.IRatingService;
import org.example.logic.user.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RatingEditHandler extends BaseHandler implements HttpHandler {
    private final IRatingService ratingService;

    public RatingEditHandler(IRatingService ratingService) {
        this.ratingService = ratingService;
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
        //System.out.println("Authenticated loggedInUser ID: " + loggedInUser.getId() + ", username: " + loggedInUser.getUsername());

        // Debugging
        //System.out.println("User is authorized: " + loggedInUser.getUsername());

        // Read InputStream
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes());
        //System.out.println("Received: " + body);

        // Use Jackson ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // Get ID from path
        Integer id = extractIdFromPath(exchange);
        if (id == null) {
            sendResponse(exchange, 400, Map.of("message", "Invalid ID"), mapper);
            return;
        }

        Rating ratingToUpdate;

        try {
            ratingToUpdate = ratingService.getRatingByIndex(id);
            if (ratingToUpdate == null) {
                sendResponse(exchange, 404, Map.of("message", "Rating not found"), mapper);
                return;
            }
        } catch (RuntimeException e) {
            // If any SQL error occurs, return a server error
            sendResponse(exchange, 500, Map.of("message", "Database error"), mapper);
            return;
        }

        System.out.println("Rating to update will be: " + ratingToUpdate);

        JsonNode jsonNode = mapper.readTree(body);
        if (jsonNode.has("stars")) {
            ratingToUpdate.setStars(jsonNode.get("stars").asInt());
        }
        if (jsonNode.has("comment")) {
            ratingToUpdate.setComment(jsonNode.get("comment").asText());
        }

        System.out.println("Updated media will be: " + ratingToUpdate);

        try {
            boolean success = ratingService.updateRating(loggedInUser, ratingToUpdate);

            // Build response
            Map<String, String> responseObject = new HashMap<>();

            // Response depends on state
            if (success) {
                responseObject.put("message", "Rating updated successfully");
                sendResponse(exchange, 201, responseObject, mapper);
            } else {
                responseObject.put("message", "Ownership error");
                sendResponse(exchange, 400, responseObject, mapper);
            }
        } catch (IllegalStateException e) {
            sendResponse(exchange, 409, Map.of("message", e.getMessage()), mapper);
        }
        catch (RuntimeException e) {
            sendResponse(exchange, 500, Map.of("message", "Internal server error"), mapper);
        }

    }
}
