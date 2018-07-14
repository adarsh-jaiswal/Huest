package com.example.amankumar.huest.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amankumar.huest.R;
import com.example.amankumar.huest.Utils.Constants;
import com.example.amankumar.huest.Utils.Utils;
import com.example.amankumar.huest.model.HuestPeople;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText hostFirstName, hostLastName, hostEmail, hostMobile, hostPassword;
    ProgressDialog mProgressDialog;
    Firebase ref, hostRef, userRef;
    String firstName, lastName, email, number, password, encodedEmail;
    SharedPreferences sp;
    String currentUser;
    private SecureRandom sr = new SecureRandom();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        Firebase.setAndroidContext(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SignUp");
        init();
    }

    private void init() {
        hostFirstName = (EditText) findViewById(R.id.hostFirstName);
        hostLastName = (EditText) findViewById(R.id.hostLastName);
        hostEmail = (EditText) findViewById(R.id.hostEmail);
        hostMobile = (EditText) findViewById(R.id.hostMobileNumber);
        hostPassword = (EditText) findViewById(R.id.Password);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(getResources().getString(R.string.progress_dialog_loading));
        mProgressDialog.setMessage(getResources().getString(R.string.progress_dialog_check_inbox));
        mProgressDialog.setCancelable(false);
    }

    public void hostRegisterHandler(View view) {
        firstName = String.valueOf(hostFirstName.getText());
        lastName = String.valueOf(hostLastName.getText());
        email = String.valueOf(hostEmail.getText()).toLowerCase();
        number = String.valueOf(hostMobile.getText());
        ref = new Firebase(Constants.FIREBASE_URL);
        password = new BigInteger(130, sr).toString(32);
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        boolean validEmail = isEmailValid(email);
        boolean validFirstName = isFirstNameValid(firstName);
        boolean validLastName = isLastNameValid(lastName);
        boolean validNumber = isNumberValid(number);
        if (!validEmail || !validFirstName || !validLastName || !validNumber)
            return;

        mProgressDialog.show();
        ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                ref.resetPassword(email, new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        mProgressDialog.dismiss();
                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SignUpActivity.this);
                        SharedPreferences.Editor spe = sp.edit();
                        spe.putString(Constants.SIGNUP_EMAIL, email).apply();
                        createUserInFireBaseHelper();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                        try {
                            startActivity(intent);
                            finish();
                        } catch (android.content.ActivityNotFoundException ex) {
                        }
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        mProgressDialog.dismiss();
                    }
                });
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                mProgressDialog.dismiss();
                if (firebaseError.getCode() == FirebaseError.EMAIL_TAKEN) {
                    hostEmail.setError("Email Already Taken");
                } else {
                    showErrorToast(firebaseError.getMessage());
                }
            }
        });
    }

    private boolean isNumberValid(String number) {
        if (number.length() < 10) {
            hostMobile.setError("Not a valid number");
            return false;
        }
        return true;
    }

    private boolean isEmailValid(String email) {
        boolean isGoodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            hostEmail.setError("Invalid Email");
            return false;
        }
        return true;
    }

    private boolean isFirstNameValid(String userName) {
        if (userName.equals("")) {
            hostFirstName.setError("Cannot be empty");
            return false;
        }
        return true;
    }

    private boolean isLastNameValid(String userName) {
        if (userName.equals("")) {
            hostLastName.setError("Cannot be empty");
            return false;
        }
        return true;
    }

    private void showErrorToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void login() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void createUserInFireBaseHelper() {
        encodedEmail = Utils.encodeEmail(email);
        currentUser = sp.getString(Constants.CURRENT_USER, null);
        userRef=new Firebase(Constants.FIREBASE_URL).child(Constants.FIREBASE_LOCATION_USERS);
        userRef.child(encodedEmail).setValue(currentUser);
        hostRef = new Firebase(Constants.FIREBASE_URL).child(currentUser).child(encodedEmail);
        HuestPeople people = new HuestPeople(firstName, encodedEmail, lastName, number);
        hostRef.setValue(people);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(Constants.CURRENT_USER, null).apply();
    }
}
