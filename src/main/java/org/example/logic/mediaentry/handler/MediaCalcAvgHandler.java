package org.example.logic.mediaentry.handler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.logic.BaseHandler;
import org.example.logic.mediaentry.service.IMediaService;
import org.example.logic.user.model.User;

import java.io.IOException;
import java.io.InputStream;

public class MediaCalcAvgHandler extends BaseHandler implements HttpHandler {
    public final IMediaService mediaService;

    public MediaCalcAvgHandler(IMediaService mediaService) {
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


    }
}
