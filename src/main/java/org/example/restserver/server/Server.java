package org.example.restserver.server;

import com.sun.net.httpserver.HttpServer;
import org.example.logic.mediaentry.handler.*;
import org.example.logic.mediaentry.persistence.IMediaRepository;
import org.example.logic.mediaentry.persistence.MediaSqlRepository;
import org.example.logic.mediaentry.service.IMediaService;
import org.example.logic.mediaentry.service.MediaService;
import org.example.logic.user.handler.LoginHandler;
import org.example.logic.user.handler.RegisterHandler;
import org.example.logic.user.persistence.IUserRepository;
import org.example.logic.user.persistence.UserSqlRepository;
import org.example.logic.user.service.IUserService;
import org.example.logic.user.service.UserService;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public void start() throws IOException {
        // User
        IUserRepository userRepository = new UserSqlRepository();
        IUserService userService = new UserService(userRepository);

        // Media entry
        IMediaRepository mediaRepository = new MediaSqlRepository();
        IMediaService mediaService = new MediaService(mediaRepository);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/users/register", new RegisterHandler(userService));
        server.createContext("/api/users/login", new LoginHandler(userService));
        server.createContext("/api/media/create", new MediaCreateHandler(mediaService));
        server.createContext("/api/media/list", new MediaListHandler(mediaService));
        server.createContext("/api/media/view", new MediaViewHandler(mediaService));
        server.createContext("/api/media/update", new MediaUpdateHandler(mediaService));
        server.createContext("/api/media/delete", new MediaDeleteHandler(mediaService));
        server.start();
        System.out.println("Server running on port 8080");
    }
}
