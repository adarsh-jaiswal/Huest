package com.example.amankumar.huest.Utils;

/**
 * Created by AmanKumar on 3/4/2016.
 */
public final class Constants {
    public static final String FIREBASE_URL="https://shop-together.firebaseio.com";
    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";
    public static final String FIREBASE_LOCATION_HOSTS="HOSTS";
    public static final String FIREBASE_LOCATION_USERS="USERS";
    public static final String FIREBASE_LOCATION_GUESTS="GUESTS";
    public static final String FIREBASE_URL_HOSTS=FIREBASE_URL+"/"+FIREBASE_LOCATION_HOSTS;
    public static final String FIREBASE_URL_GUESTS=FIREBASE_URL+"/"+FIREBASE_LOCATION_GUESTS;
    public static final String FIREBASE_URL_USERS=FIREBASE_URL+"/"+FIREBASE_LOCATION_USERS;
    public static final String CURRENT_USER_ENCODED_EMAIL ="email";
    public static final String SIGNUP_EMAIL="SignUpEmail";
    public static final String HAS_LOGGED_FOR_THE_FIRST_TIME="hasLoggedInForTheFirstTime";
    public static final String CURRENT_USER="user";

}
