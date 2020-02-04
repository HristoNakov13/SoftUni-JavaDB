package com.gamestore.services.messages;

public class GameMessages {
    public static String INVALID_TITLE = "Title must begin with an uppercase letter and must have length between 3 and 100 symbols";
    public static String INVALID_PRICE = "Price must be a positive number with precision up to 2 digits after the floating point";
    public static String INVALID_SIZE = "Size must be a positive number with precision up to 1 digit after the floating point.";
    public static String INVALID_TRAILER = "Trailer must be a youtube URL";
    public static String INVALID_THUMBNAIL = "Invalid thumbnail URL";
    public static String INVALID_DESCRIPTION = "Description must be at least 20 symbols";
    public static String INVALID_DATE = "Date must be in the format YYYY-MM-DD";


    public static String NO_LOGGED_USER = "Please login into your admin account to add games.";
    public static String UNAUTHORIZED = "Only Admin accounts allowed to add games!";


    public static String VALID_GAME = "Valid.";
    public static String SUCC_ADDED_GAME = "Added %s";

    public static String DELETE_INVALID_ID = "Invalid game ID.";
    public static String DELETE_ID_NOT_EXIST = "No game with such ID exists.";
    public static String DELETE_SUCCESS = "Successfully deleted game.";
}
