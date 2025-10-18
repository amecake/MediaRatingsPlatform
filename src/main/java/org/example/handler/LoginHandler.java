package org.example.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Postman is sending a request
        // Check if request method is POST
        // Can only be POST, no GET for example
        if (!exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        // Save Postman's request body
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes());

        // Print Postman's request body
        System.out.println("Received: " + body);
    }
}
