package org.example.handler;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new RootHandler());
        server.createContext("/api/users/register", new RegisterHandler());
        server.createContext("/api/users/login", new LoginHandler());
        server.createContext("/api/media/create", new MediaCreateHandler());
        server.createContext("/api/media/list", new MediaListHandler());
        server.createContext("/api/media/view", new MediaViewHandler());
        server.createContext("/api/media/update", new MediaUpdateHandler());
        server.createContext("/api/media/delete", new MediaDeleteHandler());
        server.start();
        System.out.println("Server running on port 8080");
    }
}
