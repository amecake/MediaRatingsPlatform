package org.example.logic.favorite.handler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.logic.BaseHandler;
import org.example.logic.favorite.service.IFavoriteService;
import org.example.logic.user.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MarkFavoriteHandler extends BaseHandler implements HttpHandler {
    public final IFavoriteService service;

    public MarkFavoriteHandler(IFavoriteService service) {
        this.service = service;
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

        // Get ID from path
        Integer id = extractIdFromPath(exchange);
        if (id == null) {
            sendResponse(exchange, 400, Map.of("message", "Invalid ID"), mapper);
            return;
        }

        try {
            String responseMessage = service.markMediaAsFavorite(user, id);

            // Build response
            Map<String, String> responseObject = new HashMap<>();

            // Response depends on state
            if (responseMessage.equals("success")) {
                responseObject.put("message", "Media entry marked as favorite successfully");
                sendResponse(exchange, 201, responseObject, mapper);
            } else {
                responseObject.put("message", "An error has occurred");
                sendResponse(exchange, 409, responseObject, mapper);
            }
        } catch (RuntimeException e) {
            // If any SQL error occurs, return a server error
            sendResponse(exchange, 500, Map.of("message", "Database error"), mapper);
        }
    }
}
