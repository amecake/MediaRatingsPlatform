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

public class LikeRatingHandler extends BaseHandler implements HttpHandler {
    private final IRatingService ratingService;

    public LikeRatingHandler(IRatingService ratingService) {
        this.ratingService = ratingService;
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
        System.out.println("Received: " + body);

        // Use Jackson ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // Get ID from path
        String path = exchange.getRequestURI().getPath(); //  /rating/create/1
        String[] parts = path.split("/");
        int rating_id;

        // Check if it's an int
        try {
            rating_id = Integer.parseInt(parts[parts.length - 1]);
        } catch (NumberFormatException e) {
            sendResponse(exchange, 400, Map.of("message", "Not an integer"), mapper);
            return;
        }

        String responseMessage = ratingService.likeRating(rating_id);

        // Build response
        Map<String, String> responseObject = new HashMap<>();

        if (responseMessage.equals("success")) {
            responseObject.put("message", "Like added successfully");
            sendResponse(exchange, 201, responseObject, mapper);
        } else {
            responseObject.put("message", "An error has occurred");
            sendResponse(exchange, 409, responseObject, mapper);
        }
    }
}
