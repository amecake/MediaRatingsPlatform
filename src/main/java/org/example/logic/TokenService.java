package org.example.logic;

import org.example.logic.user.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TokenService {
    // Store token for current session (like in php)
    private static final Map<String, User> tokenMap = new HashMap<>();

    public static String generateToken(User user) {
        String token = user.getUsername() + "-mrpToken";
        tokenMap.put(token, user); // store that this token belongs to that user
        return token;
    }

    public static boolean isTokenValid(String token) {
        return tokenMap.containsKey(token); // true if the token exists
    }

    public static User getUserByToken(String token) {
        return tokenMap.get(token); // get the user who owns the token
    }

    public static Set<String> getAllTokens() {
        return tokenMap.keySet();
    }

}
