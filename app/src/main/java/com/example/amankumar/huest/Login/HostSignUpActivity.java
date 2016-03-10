package com.example.amankumar.huest.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amankumar.huest.R;
import com.example.amankumar.huest.Utils.Constants;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class HostSignUpActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText hostFirstName, hostLastName, hostEmail, hostMobile, hostPassword;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_sign_up);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        Firebase.setAndroidContext(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SignUp As Host");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    private void init() {
        hostFirstName = (EditText) findViewById(R.id.hostFirstName);
        hostLastName = (EditText) findViewById(R.id.hostLastName);
        hostEmail = (EditText) findViewById(R.id.hostEmail);
        hostMobile = (EditText) findViewById(R.id.hostMobileNumber);
        hostPassword = (EditText) findViewById(R.id.Password);
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setTitle(getResources().getString(R.string.progress_dialog_loading));
        mProgressDialog.setMessage(getResources().getString(R.string.progress_dialog_creating_user_with_firebase));
        mProgressDialog.setCancelable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_host_sign_up, menu);
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
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public void hostRegisterHandler(View view) {
        String firstName = String.valueOf(hostFirstName.getText());
        String lastName = String.valueOf(hostLastName.getText());
        String email = String.valueOf(hostEmail.getText()).toLowerCase();
        String number = String.valueOf(hostMobile.getText());
        String password = String.valueOf(hostPassword.getText());
        Firebase ref = new Firebase(Constants.FIREBASE_URL);

        boolean validEmail=isEmailValid(email);
        boolean validFirstName =isFirstNameValid(firstName);
        boolean validPassword=isPasswordValid(password);
        boolean validLastName=isLastNameValid(lastName);
        boolean validNumber=isNumberValid(number);
        if(!validEmail || !validFirstName || !validPassword ||!validLastName ||!validNumber)
            return;

        mProgressDialog.show();

        ref.createUser(email, password, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                mProgressDialog.dismiss();
                login();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                mProgressDialog.dismiss();
                if(firebaseError.getCode()==FirebaseError.EMAIL_TAKEN){
                    hostEmail.setError("Email Already Taken");
                }
                else {
                    showErrorToast(firebaseError.getMessage());
                }
            }
        });
    }

    private boolean isNumberValid(String number) {
        if(number.length()<10){
            hostMobile.setError("Not a valid number");
            return  false;
        }
        return  true;
    }

    private boolean isEmailValid(String email) {
        boolean isGoodEmail=(email!=null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if(!isGoodEmail){
            hostEmail.setError("Invalid Email");
            return false;
        }
        return isGoodEmail;
    }
    private boolean isFirstNameValid(String userName){
        if(userName.equals("")){
            hostFirstName.setError("Cannot be empty");
            return  false;
        }
        return true;
    }
    private boolean isLastNameValid(String userName){
        if(userName.equals("")){
            hostLastName.setError("Cannot be empty");
            return  false;
        }
        return true;
    }
    private boolean isPasswordValid(String password){
        if(password.length()<6){
            hostPassword.setError("Should contain more than 6 letters");
            return  false;
        }
        return  true;
    }
    private  void showErrorToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
    private void login(){
        Intent intent=new Intent(this,LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
