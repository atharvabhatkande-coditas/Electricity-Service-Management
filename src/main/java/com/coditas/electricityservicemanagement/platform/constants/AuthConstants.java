package com.coditas.electricityservicemanagement.platform.constants;

public final class AuthConstants {
    private AuthConstants(){

    }
    public static final String USER_NOT_FOUND="User not found";
    public static final String USER_EXIST="User Already Exist";
    public static final String UNAUTHORIZED="Unauthorized";
    public static final String LOGIN_FAILURE="Please Login Again";
    public static final String VERIFY_CODE="Please Verify your email";
    public static final String REGISTRATION_SUCCESS="Registration Successful";
    public static final String SESSION_EXPIRED="Session Expired ! Please Login Again";
    public static final String LOGOUT="Logged Out Successfully";



    public static final String EMAIL_TEXT= """
            Your Invitation Link: %s
            
            Your Invitation Code: %s
            """;
    public static final String INVITATION_LINK="https://coming-revivable-scandal.ngrok-free.dev/electrohub/v1/auth/platform-register";
    public static final String EMAIL_SENT="Invitation Sent Successfully";

}
