package com.example.amankumar.huest.model;

import com.example.amankumar.huest.Utils.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ServerValue;

import java.util.HashMap;

/**
 * Created by AmanKumar on 3/5/2016.
 */
public class HuestPeople {
    String email;
    String firstName;
    String lastName;
    String mobile;
    String password;
    private HashMap<String, Object> timestampLastChanged;

    public HuestPeople() {
    }

    public HuestPeople(String firstName, String email, String lastName, String password, String mobile) {
        this.firstName = firstName;
        this.email = email;
        this.lastName = lastName;
        this.password = password;
        this.mobile = mobile;
        HashMap<String, Object> timeStampLastChangedObj = new HashMap<>();
        timeStampLastChangedObj.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
        this.timestampLastChanged = timeStampLastChangedObj;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public HashMap<String, Object> getTimestampLastChanged() {
        return timestampLastChanged;
    }


    @JsonIgnore
    public long getTimestampLastChangedLong() {

        return (long) timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }
}
