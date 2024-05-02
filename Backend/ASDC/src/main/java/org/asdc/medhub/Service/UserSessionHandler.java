package org.asdc.medhub.Service;

import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Component responsible for managing user sessions.
 */
@Component
public class UserSessionHandler {
    private final ConcurrentHashMap<String, String> userSessions = new ConcurrentHashMap<>();

    /**
     * Registers a session for the specified user.
     *
     * @param userId The ID of the user.
     * @param sessionId The session ID associated with the user.
     */
    public void registerSession(String userId, String sessionId) {
        userSessions.put(userId, sessionId);
    }

    /**
     * Retrieves the session ID associated with the specified user.
     *
     * @param userId The ID of the user.
     * @return The session ID associated with the user.
     */
    public String getSessionId(String userId) {
        return userSessions.get(userId);
    }
}
