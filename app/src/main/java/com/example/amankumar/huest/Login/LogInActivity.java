package com.example.amankumar.huest.Login;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amankumar.huest.R;
import com.example.amankumar.huest.UI.HuestActivity;
import com.example.amankumar.huest.Utils.Constants;
import com.example.amankumar.huest.Utils.Utils;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class LogInActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText emailEditText, passwordEditText;
    ProgressDialog mProgressDialog;
    Firebase ref,userRef,currentUserRef;
    String encodedEmail,email,user,currentUser;
    SharedPreferences sharedPref;
    SharedPreferences.Editor spe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        Firebase.setAndroidContext(this);
        setSupportActionBar(toolbar);
        init();
        sharedPref= PreferenceManager.getDefaultSharedPreferences(LogInActivity.this);
        spe=sharedPref.edit();
        if(sharedPref.getString(Constants.CURRENT_USER_ENCODED_EMAIL,null)!=null){
            takeUserToHuestActivity();
        }
    }

    private void takeUserToHuestActivity() {
        Intent intent = new Intent(LogInActivity.this, HuestActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void init() {
        emailEditText = (EditText) findViewById(R.id.userIdText);
        passwordEditText = (EditText) findViewById(R.id.Password);
        ref = new Firebase(Constants.FIREBASE_URL);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(getString(R.string.progress_dialog_loading));
        mProgressDialog.setMessage(getString(R.string.progress_dialog_authenticating_with_firebase));
        mProgressDialog.setCancelable(true);
    }
    public void signUpButton(View view) {
        Intent intent = new Intent(this,GuestOrHuestActivity.class);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Bundle bundle= ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
            startActivity(intent,bundle);
        }
        else
        startActivity(intent);
    }

    public void loginButton(View view) {
        email = emailEditText.getText().toString().toLowerCase();
        String password = passwordEditText.getText().toString();
        encodedEmail=Utils.encodeEmail(email);
        if (email.equals("")) {
            emailEditText.setError("Email cannot be empty");
            return;
        }
        if (password.equals("")){
            passwordEditText.setError("Field cannot be empty");
            return;
        }
        mProgressDialog.show();
        ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                mProgressDialog.dismiss();
                if (authData != null) {
                    spe.putString(Constants.CURRENT_USER_ENCODED_EMAIL,encodedEmail).apply();
                    currentUserRef=new Firebase(Constants.FIREBASE_URL).child(Constants.FIREBASE_LOCATION_USERS);
                    currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            user = (String) dataSnapshot.child(encodedEmail).getValue();
                            spe.putString(Constants.CURRENT_USER, user).apply();
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                   /* userRef=new Firebase(Constants.FIREBASE_URL).child(currentUser).child(encodedEmail);
                    spe.putString(Constants.CURRENT_USER,currentUser).apply();
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            HuestPeople people=dataSnapshot.getValue(HuestPeople.class);
                            if(people!=null){
                                if(!people.isHasLoggedInForTheFirstTime())
                                {
                                    userRef.changePassword(email, passwordEditText.getText().toString(), passwordEditText.getText().toString(), new Firebase.ResultHandler() {
                                        @Override
                                        public void onSuccess() {
                                            userRef.child(Constants.HAS_LOGGED_FOR_THE_FIRST_TIME).setValue(true);
                                        }

                                        @Override
                                        public void onError(FirebaseError firebaseError) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });*/
                    takeUserToHuestActivity();
                }
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                mProgressDialog.dismiss();
                switch (firebaseError.getCode()) {
                    case FirebaseError.INVALID_EMAIL:
                    case FirebaseError.USER_DOES_NOT_EXIST:
                        emailEditText.setError(getString(R.string.error_message_email_issue));
                        break;
                    case FirebaseError.INVALID_PASSWORD:
                        passwordEditText.setError(firebaseError.getMessage());
                        break;
                    case FirebaseError.NETWORK_ERROR:
                        showErrorToast(getString(R.string.error_message_failed_sign_in_no_network));
                        break;
                    default:
                        showErrorToast(firebaseError.toString());
                }

            }
        });
    }

    private void showErrorToast(String s) {
        Toast.makeText(LogInActivity.this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        String signedUpEmail=sharedPref.getString(Constants.SIGNUP_EMAIL,null);
        if(signedUpEmail!=null){
            emailEditText.setText(signedUpEmail);
            spe.putString(Constants.SIGNUP_EMAIL,null).apply();
        }
    }
}
