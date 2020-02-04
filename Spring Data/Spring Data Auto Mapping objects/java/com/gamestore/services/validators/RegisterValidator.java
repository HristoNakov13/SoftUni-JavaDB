package com.gamestore.services.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterValidator {
    public static boolean isValidFullName(String fullName) {
        return !fullName.isEmpty();
    }

    public static boolean isValidPassword(String password, String confirmPassword) {
        if (password.length() < 6 || !password.equals(confirmPassword)) {
            return false;
        }

        String lowerUpperCheck = "^(?=[A-Z]*[a-z])(?=[a-z]*[A-Z])[a-zA-Z0-9]+$";
        String digitCheck = "[0-9]+";

        Pattern lowerUpperPattern = Pattern.compile(lowerUpperCheck);
        Pattern digitPattern = Pattern.compile(digitCheck);

        Matcher caseMatcher = lowerUpperPattern.matcher(password);
        Matcher digitMatcher = digitPattern.matcher(password);

        return  caseMatcher.find() && digitMatcher.find();
    }

    public static boolean isValidEmail(String email) {
        Pattern emailPattern = Pattern.compile("\\w+@[a-zA-Z]+\\.[a-zA-Z]+");
        Matcher emailMatcher = emailPattern.matcher(email);

        return emailMatcher.find();
    }
}
