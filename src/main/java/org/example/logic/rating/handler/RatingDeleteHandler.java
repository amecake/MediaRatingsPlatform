package org.example.logic.rating.handler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.logic.BaseHandler;
import org.example.logic.rating.service.IRatingService;
import org.example.logic.user.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RatingDeleteHandler extends BaseHandler implements HttpHandler {
    private final IRatingService ratingService;

    public RatingDeleteHandler(IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Verify correct request method
        boolean valid = verifyRequestMethod(exchange, "DELETE");
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
        Integer id = extractIdFromPath(exchange);
        if (id == null) {
            sendResponse(exchange, 400, Map.of("message", "Invalid ID"), mapper);
            return;
        }

        try {
            String responseMessage = ratingService.deleteRating(loggedInUser, id);

            // Build response
            Map<String, String> responseObject = new HashMap<>();

            // Response depends on state
            if (responseMessage.equals("success")) {
                responseObject.put("message", "Rating deleted successfully");
                sendResponse(exchange, 201, responseObject, mapper);
            } else {
                responseObject.put("message", responseMessage);
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
