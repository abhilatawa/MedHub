package org.asdc.medhub.Service;

import io.jsonwebtoken.Jwts;
import org.asdc.medhub.Configuration.CustomConfigurations;
import org.asdc.medhub.Service.Interface.IJwtService;
import org.asdc.medhub.Utility.Enums.UserRole;
import javax.crypto.SecretKey;
import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.function.Function;

/**
 * Contains supporting members for Jwt token authentication
 */
@Service
public class JwtService implements IJwtService {

    /**
     * Object holding Custom configuration
     */
    private final CustomConfigurations customConfigurations;

    /**
     * Parameterized constructor
     * @param customConfigurations custom configuration
     */
    public JwtService(CustomConfigurations customConfigurations){
        this.customConfigurations=customConfigurations;
    }

    //region PUBLIC METHODS
    /**
     * Extracts username from token
     * @param token - jwt token string
     * @return String
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts issuer name from token
     * @param token - jwt token string
     * @return String
     */
    public String extractIssuer(String token){
        return extractClaim(token,Claims::getIssuer);
    }

    /**
     * Extracts userId from the jwt token
     * @param token - jwt token string
     * @return String
     */
    public String extractUserId(String token){
        return  extractClaim(token,Claims::getId);
    }

    /**
     * Check if token is valid for given user
     * @param token - jwt token string
     * @param user - username fetched from database
     * @return boolean
     */
    public boolean isValid(String token, UserDetails user, String userId) {
        boolean isTokenValid;
        String usernameFromToken = extractUsername(token);
        String issuerFromToken=extractIssuer(token);
        String userIdFromToken=extractUserId(token);

        isTokenValid=(usernameFromToken.equals(user.getUsername()))
                && this.customConfigurations.issuerName.equals(issuerFromToken)
                && !isTokenExpired(token)
                && userId.equals(userIdFromToken);
        return isTokenValid;
    }

    /**
     * Extracts specific claim from jwt token
     * @param token - jwt token string
     * @param resolver - claim name
     * @return T returns generic type
     * @param <T> generic parameter
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    /**
     * Generated JWT token string from given parameters
     * @param userName - username of user
     * @param role - role of user
     * @param userId - id of user from tbl_users
     * @return String
     */
    public String generateToken(String userName,String userId, UserRole role) {
       return   Jwts
                .builder()
                .subject(userName)
                .issuer(this.customConfigurations.issuerName)
                .claim("role",role)
                .claim("userId",userId)
                .id(userId)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (long) this.customConfigurations.tokenValidityInMinutes *60*60 ))
                .signWith(getSigninKey())
                .compact();
    }
    //endregion

    //region PRIVATE METHODS

    /**
     * Checks is given token is expired or not
     * @param token - jwt token string
     * @return boolean
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts expiration time from jwt token string
     * @param token - jwt token string
     * @return Date object
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from token string
     * @param token - jwt token string
     * @return Claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Returns HMAc SHA key from SECRET_KEY final member
     * @return SecretKey
     */
    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(this.customConfigurations.jwtTokenSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    //endregion
}
