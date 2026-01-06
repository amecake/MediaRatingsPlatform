package org.example.restserver.server;

import com.sun.net.httpserver.HttpServer;
import org.example.logic.mediaentry.handler.*;
import org.example.logic.mediaentry.persistence.IMediaRepository;
import org.example.logic.mediaentry.persistence.MediaSqlRepository;
import org.example.logic.mediaentry.service.IMediaService;
import org.example.logic.mediaentry.service.MediaService;
import org.example.logic.rating.handler.LikeRatingHandler;
import org.example.logic.rating.handler.RatingAddHandler;
import org.example.logic.rating.persistence.IRatingRepository;
import org.example.logic.rating.persistence.RatingSqlRepository;
import org.example.logic.rating.service.IRatingService;
import org.example.logic.rating.service.RatingService;
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

        // Rating
        IRatingRepository ratingRepository = new RatingSqlRepository();
        IRatingService ratingService = new RatingService(ratingRepository);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        // User
        server.createContext("/api/users/register", new RegisterHandler(userService));
        server.createContext("/api/users/login", new LoginHandler(userService));

        // Media entry
        server.createContext("/api/media/create", new MediaCreateHandler(mediaService));
        server.createContext("/api/media/list", new MediaListHandler(mediaService));
        server.createContext("/api/media/view", new MediaViewHandler(mediaService));
        server.createContext("/api/media/update", new MediaUpdateHandler(mediaService));
        server.createContext("/api/media/delete", new MediaDeleteHandler(mediaService));
        server.createContext("/api/media/calc-avg", new MediaCalcAvgHandler(mediaService));

        // Rating
        server.createContext("/api/rating/add", new RatingAddHandler(ratingService));
        server.createContext("/api/rating/like", new LikeRatingHandler(ratingService));

        server.start();
        System.out.println("Server running on port 8080");
    }
}
