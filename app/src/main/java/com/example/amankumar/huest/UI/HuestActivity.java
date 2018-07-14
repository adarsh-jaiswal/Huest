package com.example.amankumar.huest.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.amankumar.huest.Login.LogInActivity;
import com.example.amankumar.huest.R;
import com.example.amankumar.huest.Utils.Constants;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

public class HuestActivity extends AppCompatActivity {
    String encodedEmail, currentUser;
    Toolbar toolbar;
    Firebase ref;
    Firebase.AuthStateListener mAuthStateListener;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huest);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Huest");
        Firebase.setAndroidContext(this);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        encodedEmail = sp.getString(Constants.CURRENT_USER_ENCODED_EMAIL, null);
        currentUser=sp.getString(Constants.CURRENT_USER,null);
        ref = new Firebase(Constants.FIREBASE_URL);
        mAuthStateListener = new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData == null) {
                    SharedPreferences.Editor spe = sp.edit();
                    spe.putString(Constants.CURRENT_USER_ENCODED_EMAIL, null).apply();
                    spe.putString(Constants.CURRENT_USER,null).apply();
                    takeUserToLoginScreenOnUnAuth();
                }
            }
        };
        ref.addAuthStateListener(mAuthStateListener);
        Toast.makeText(HuestActivity.this, "Welcome:" + currentUser+encodedEmail, Toast.LENGTH_SHORT).show();
    }

    private void takeUserToLoginScreenOnUnAuth() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_host, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.logout) {
            ref.unauth();
        }
        if (id == R.id.action_change_password) {
            Intent intent = new Intent(this, ChangePassword.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ref.removeAuthStateListener(mAuthStateListener);
    }
}
