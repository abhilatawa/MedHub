package org.asdc.medhub.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Custom configuration class holding values from application.properties
 */
@Configuration
public class CustomConfigurations {

    /**
     * Profile picture folder path for windows operating system
     */
    @Value("${image.storage.windows-path}")
    private String windowsPath;

    /**
     * Profile picture folder path for linux/mac operating system
     */
    @Value("${image.storage.linux-path}")
    private String linuxPath;

    /**
     * Secret key to sign the JWT authentication token
     */
    @Value("${spring.medhub.authentication.secret-key}")
    public String jwtTokenSigningKey;

    /**
     * Issuer name for JWT
     */
    @Value("${spring.medhub.authentication.issuer-name}")
    public String issuerName;

    /**
     * JWT token validity in minutes
     */
    @Value("${spring.medhub.authentication.token-validity-in-minutes}")
    public int tokenValidityInMinutes;

    /**
     * Allowed origin for frontend
     */
    @Value("${spring.medhub.frontend_url}")
    public String frontEndAppUrl;

    /**
     * Returns profile picture path according to the OS in which application is running
     * @return String full folder path for profile pictures
     */
    public String getProfilePictureFolderPath (){return System.getProperty("os.name").toLowerCase().contains("win")?windowsPath:linuxPath;}
}
