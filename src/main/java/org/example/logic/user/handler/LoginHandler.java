package org.example.logic.user.handler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.logic.BaseHandler;
import org.example.logic.TokenService;
import org.example.logic.user.model.User;
import org.example.logic.user.service.IUserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class LoginHandler extends BaseHandler implements HttpHandler {
    private final IUserService userService;

    public LoginHandler(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Verify correct request method
        boolean valid = verifyRequestMethod(exchange, "POST");
        if (!valid) return;

        // Read InputStream
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes());
        //System.out.println("Received: " + body);

        // Use Jackson ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // Deserialize JSON into object
        User user = mapper.readValue(body, User.class);

        // Debugging
        //System.out.println("Parsed user: \n" + user);

        try {
            // This just checks if the credentials are right
            String loginResult = userService.credentialsCheck(user);

            Map<String, String> responseObject = new HashMap<>();

            if ("success".equals(loginResult)) {
                // Fetch the full user with ID
                User userFromDb = userService.getUserByUsername(user.getUsername());

                // Generate token for the full user
                // This token is like a validity check
                // Any time a request will be sent, it also checks this token
                // Only if it's valid, the user can do stuff
                String token = TokenService.generateToken(userFromDb);
                System.out.println("Generated token: " + token);
                System.out.println("All tokens now: " + TokenService.getAllTokens());

                responseObject.put("token", token);
                sendResponse(exchange, 201, responseObject, mapper);
            } else {
                // Keep your current error messages
                responseObject.put("message", loginResult);
                sendResponse(exchange, 409, responseObject, mapper);
            }
        } catch (RuntimeException e) {
            // If any SQL error occurs, return a server error
            sendResponse(exchange, 500, Map.of("message", "Database error"), mapper);
        }
    }
}
