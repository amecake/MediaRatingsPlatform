package org.example.handler;

import com.sun.net.httpserver.HttpServer;
import org.example.persistence.IUserRepository;
import org.example.persistence.UserSqlRepository;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new RootHandler());
        server.createContext("/register", new RegisterHandler());
        server.createContext("/login", new LoginHandler());
        server.start();
        System.out.println("Server running on port 8080");
    }
}
