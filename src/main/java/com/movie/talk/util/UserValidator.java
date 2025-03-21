package com.movie.talk.util;

import java.util.regex.Pattern;

public class UserValidator {

    private static final String ID_REGEX = "^[a-zA-Z0-9]{6,12}$";

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$";

    private static final String NICKNAME_REGEX = "^[가-힣\\w\\d]{2,10}$";

    public static boolean isValidId(String id) {
        return Pattern.matches(ID_REGEX, id);
    }

    public static boolean isValidPassword(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    public static boolean isValidNickname(String nickname) {
        return Pattern.matches(NICKNAME_REGEX, nickname);
    }
}
