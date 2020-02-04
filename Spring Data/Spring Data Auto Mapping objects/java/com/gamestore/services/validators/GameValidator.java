package com.gamestore.services.validators;

import com.gamestore.services.messages.GameMessages;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameValidator {
//    Title – has to begin with an uppercase letter and must have length between 3 and 100 symbols (inclusively).
    public static boolean isValidTitle(String title) {
        Pattern titlePattern = Pattern.compile("^[A-Z]{1,1}[a-z]{2,99}$");
        Matcher match = titlePattern.matcher(title);

        return match.find();
    }

//    Price – must be a positive number with precision up to 2 digits after the floating point.
    public static boolean isValidPrice(String price) {
        try {
            BigDecimal parsedPrice = new BigDecimal(price);
            boolean hasAccuratePrecision = parsedPrice.scale() == 2;

            return parsedPrice.doubleValue() > 0 && hasAccuratePrecision;
        } catch (NumberFormatException e) {
            return false;
        }
    }

//    Size – must be a positive number with precision up to 1 digit after the floating point.
    public static boolean isValidSize(String size) {
        try {
            BigDecimal parsedSize = new BigDecimal(size);
            boolean hasAccuratePrecision = parsedSize.scale() == 1;

            return parsedSize.doubleValue() > 0 && hasAccuratePrecision;
        } catch (NumberFormatException e) {
            return false;
        }
    }

//    Trailer – only videos from YouTube are allowed.
//    Only their ID, which is a string of exactly 11 characters, should be saved to the database.
    public static boolean isValidTrailer(String trailerURL) {
        String youtubeURLRegex = "^((https:\\/\\/www\\.)|(https:\\/\\/)|(www\\.)|(http:\\/\\/)|(http:\\/\\/www\\.)|())" +
                "youtube\\.com\\/watch\\?v=.{11}$";

        Pattern urlPattern = Pattern.compile(youtubeURLRegex);
        Matcher matcher = urlPattern.matcher(trailerURL);

        return matcher.find();
    }
//    Thumbnail URL – it should be a plain text starting with http://, https:// or null
    public static boolean isValidThumbnail(String thumbnailURl) {
        String thumbnailRegex = "^((https:\\/\\/)|(http:\\/\\/)|()).+$";

        Pattern thumbnailPattern = Pattern.compile(thumbnailRegex);
        Matcher matcher = thumbnailPattern.matcher(thumbnailURl);

        return matcher.find();
    }

    public static boolean isValidDescription(String description) {
        return description.length() >= 20;
    }

//    Matches only dates in the format yyyy-mm-dd
    public static boolean isValidReleaseDate(String releaseDate) {
        String dateRegex = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
        Pattern datePattern = Pattern.compile(dateRegex);

        return datePattern.matcher(releaseDate).find();
    }

    public static String validateGame(String[] args) {
        if (!GameValidator.isValidTitle(args[0])) {
            return GameMessages.INVALID_TITLE;
        }

        if (!GameValidator.isValidPrice(args[1])) {
            return GameMessages.INVALID_PRICE;
        }

        if (!GameValidator.isValidSize(args[2])) {
            return GameMessages.INVALID_SIZE;
        }

        if (!GameValidator.isValidTrailer(args[3])) {
            return GameMessages.INVALID_TRAILER;
        }

        if (!GameValidator.isValidThumbnail(args[4])) {
            return GameMessages.INVALID_THUMBNAIL;
        }

        if (!GameValidator.isValidThumbnail(args[4])) {
            return GameMessages.INVALID_THUMBNAIL;
        }

        if (!GameValidator.isValidDescription(args[5])) {
            return GameMessages.INVALID_DESCRIPTION;
        }

        if (!GameValidator.isValidReleaseDate(args[6])) {
            return GameMessages.INVALID_DATE;
        }

        return GameMessages.VALID_GAME;
    }
}
