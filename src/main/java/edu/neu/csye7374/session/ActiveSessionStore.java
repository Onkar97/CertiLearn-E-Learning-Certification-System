package edu.neu.csye7374.session;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActiveSessionStore {
    private final Map<String, String> activeSessions = new ConcurrentHashMap<>();

    public void addSession(String username, String token) { activeSessions.put(username, token); }
    public void removeSession(String username) { activeSessions.remove(username); }
    public boolean isTokenActive(String username, String token) {
        return token != null && token.equals(activeSessions.get(username));
    }
}
