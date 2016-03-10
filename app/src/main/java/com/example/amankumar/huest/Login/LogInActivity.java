package com.example.amankumar.huest.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amankumar.huest.R;
import com.example.amankumar.huest.UI.HostActivity;
import com.example.amankumar.huest.Utils.Constants;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LogInActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button loginButton;
    Button signUpButton;
    EditText emailEditText, passwordEditText;
    ProgressDialog mProgressDialog;
    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        Firebase.setAndroidContext(this);
        setSupportActionBar(toolbar);
        init();
    }

    private void init() {
        loginButton = (Button) findViewById(R.id.logInButton);
        signUpButton = (Button) findViewById(R.id.logInButton);
        emailEditText = (EditText) findViewById(R.id.userIdText);
        passwordEditText = (EditText) findViewById(R.id.Password);
        ref = new Firebase(Constants.FIREBASE_URL);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(getString(R.string.progress_dialog_loading));
        mProgressDialog.setMessage(getString(R.string.progress_dialog_authenticating_with_firebase));
        mProgressDialog.setCancelable(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void signUpButton(View view) {
        Intent intent = new Intent(this, GuestOrHostActivity.class);
        startActivity(intent);
    }

    public void loginButton(View view) {
        String email = emailEditText.getText().toString().toLowerCase();
        String password = passwordEditText.getText().toString();
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
                    Intent intent = new Intent(LogInActivity.this, HostActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
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
}
