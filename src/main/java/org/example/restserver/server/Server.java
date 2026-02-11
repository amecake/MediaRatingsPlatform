package org.example.restserver.server;

import com.sun.net.httpserver.HttpServer;
import org.example.logic.favorite.handler.DeleteFavoriteHandler;
import org.example.logic.favorite.handler.MarkFavoriteHandler;
import org.example.logic.favorite.handler.ViewFavoritesHandler;
import org.example.logic.favorite.repository.FavoriteSqlRepository;
import org.example.logic.favorite.repository.IFavoriteRepository;
import org.example.logic.favorite.service.FavoriteService;
import org.example.logic.favorite.service.IFavoriteService;
import org.example.logic.mediaentry.handler.*;
import org.example.logic.mediaentry.persistence.IMediaRepository;
import org.example.logic.mediaentry.persistence.MediaSqlRepository;
import org.example.logic.mediaentry.service.IMediaService;
import org.example.logic.mediaentry.service.MediaService;
import org.example.logic.rating.handler.LikeRatingHandler;
import org.example.logic.rating.handler.RatingAddHandler;
import org.example.logic.rating.handler.RatingDeleteHandler;
import org.example.logic.rating.handler.RatingEditHandler;
import org.example.logic.rating.persistence.IRatingRepository;
import org.example.logic.rating.persistence.RatingSqlRepository;
import org.example.logic.rating.service.IRatingService;
import org.example.logic.rating.service.RatingService;
import org.example.logic.user.handler.LoginHandler;
import org.example.logic.user.handler.RegisterHandler;
import org.example.logic.user.handler.ViewProfileHandler;
import org.example.logic.user.persistence.IUserRepository;
import org.example.logic.user.persistence.UserSqlRepository;
import org.example.logic.user.service.IUserService;
import org.example.logic.user.service.UserService;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public void start() throws IOException {
        // Users
        IUserRepository userRepository = new UserSqlRepository();
        IUserService userService = new UserService(userRepository);

        // Media entries
        IMediaRepository mediaRepository = new MediaSqlRepository();
        IMediaService mediaService = new MediaService(mediaRepository);

        // Ratings
        IRatingRepository ratingRepository = new RatingSqlRepository();
        IRatingService ratingService = new RatingService(ratingRepository);

        // Favorites
        IFavoriteRepository favoriteRepository = new FavoriteSqlRepository();
        IFavoriteService favoriteService = new FavoriteService(favoriteRepository);


        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Users
        server.createContext("/api/users/register", new RegisterHandler(userService));
        server.createContext("/api/users/login", new LoginHandler(userService));
        server.createContext("/api/users/profile", new ViewProfileHandler(userService));

        // Media entry
        server.createContext("/api/media/create", new MediaCreateHandler(mediaService));
        server.createContext("/api/media/view", new MediaViewHandler(mediaService));
        server.createContext("/api/media/update", new MediaUpdateHandler(mediaService));
        server.createContext("/api/media/delete", new MediaDeleteHandler(mediaService));
        server.createContext("/api/media/calc-avg", new MediaCalcAvgHandler(mediaService));

        // Rating
        server.createContext("/api/rating/add", new RatingAddHandler(ratingService));
        server.createContext("/api/rating/edit", new RatingEditHandler(ratingService));
        server.createContext("/api/rating/delete", new RatingDeleteHandler(ratingService));
        server.createContext("/api/rating/like", new LikeRatingHandler(ratingService));

        // Favorites
        server.createContext("/api/favorite/create", new MarkFavoriteHandler(favoriteService));
        server.createContext("/api/favorite/delete", new DeleteFavoriteHandler(favoriteService));
        server.createContext("/api/favorite/view", new ViewFavoritesHandler(favoriteService));

        server.start();
        System.out.println("Server running on port 8080");
    }
}
