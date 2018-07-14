package com.example.amankumar.huest.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.amankumar.huest.R;
import com.example.amankumar.huest.Utils.Constants;

public class GuestOrHuestActivity extends AppCompatActivity {
    Toolbar toolbar;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_or_huest);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SignUp As");
        sp= PreferenceManager.getDefaultSharedPreferences(this);

    }

    public void signUpAsHostButton(View view) {
        SharedPreferences.Editor spe=sp.edit();
        spe.putString(Constants.CURRENT_USER, Constants.FIREBASE_LOCATION_HOSTS).apply();
        signUp();
    }

    private void signUp() {
        Intent intent=new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

    public void signUpAsGuestButton(View view) {
        SharedPreferences.Editor spe=sp.edit();
        spe.putString(Constants.CURRENT_USER,Constants.FIREBASE_LOCATION_GUESTS).apply();
        signUp();
    }
}
