package org.asdc.medhub.Service.Interface;

import io.jsonwebtoken.Claims;
import org.asdc.medhub.Utility.Enums.UserRole;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

/**
 * Interface for JwtService
 */
public interface IJwtService {

        /**
         * Extracts username from token
         * @param token - jwt token string
         * @return String
         */
        String extractUsername(String token);

        /**
         * Check if token is valid for given user
         * @param token - jwt token string
         * @param user - username fetched from database
         * @return boolean
         */
        boolean isValid(String token, UserDetails user, String userId);

        /**
         * Extracts specific claim from jwt token
         * @param token - jwt token string
         * @param resolver - claim name
         * @return T returns generic type
         * @param <T> generic parameter
         */
        <T> T extractClaim(String token, Function<Claims, T> resolver);

        String extractUserId(String token);

        /**
         * Generated JWT token string from given parameters
         * @param userName - username of user
         * @param role - role of user
         * @param userId - id of user from tbl_users
         * @return String
         */
        String generateToken(String userName, String userId, UserRole role);
}
