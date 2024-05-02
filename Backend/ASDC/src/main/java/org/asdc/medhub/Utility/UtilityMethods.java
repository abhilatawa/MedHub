package org.asdc.medhub.Utility;

import org.apache.commons.codec.digest.DigestUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.*;
import java.util.Base64;

/**
 * This class contains common utility methods (mostly static methods)
 */
public class UtilityMethods {

    //region PUBLIC METHODS
    /**
     * Convert string into its SHA256 hash value
     * @param inputString - string which need to be hashed
     * @return String - SHA256 hash value
     */
    public static String getSha256HashString(String inputString){
       return DigestUtils.sha256Hex(inputString);
    }

    /**
     * Retrieves the Base64 encoded string representation of an image file located at the specified file path.
     *
     * @param fileName The path to the image file.
     * @return The Base64 encoded string representation of the image file.
     */
    public static String getBase64StringFromImage(String profilePictureFolderPath,String fileName){
        String base64String = null;
        Path path = Paths.get(profilePictureFolderPath,fileName);
        byte[] imageBytes;
        try {
            imageBytes = Files.readAllBytes(path);
            base64String=Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            System.out.println("File unable to read: "+ fileName);
        }
        return  base64String;
    }

    /**
     * Gets day of week from local date obejct
     * @param date date
     * @return DayOfWeek(MONDAY,TUESDAY...)
     */
    public static DayOfWeek getWeekDayFromDate(LocalDate date) {
        return date.getDayOfWeek();
    }

    //endregion
}