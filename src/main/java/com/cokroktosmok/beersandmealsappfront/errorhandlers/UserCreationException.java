package com.cokroktosmok.beersandmealsappfront.errorhandlers;

public class UserCreationException extends Exception{

    public static String ERR_MISSING_INFORMATION = "Information provided in form was incomplete or invalid.";
    public static String ERR_LOGIN_TAKEN = "Login was already taken.";
    public static String ERR_EMAIL_TAKEN = "Account with specified email already exist.";

    public UserCreationException(String message) {
        super(message);
    }

}
