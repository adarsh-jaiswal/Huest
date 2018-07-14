package com.example.amankumar.huest.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amankumar.huest.R;
import com.example.amankumar.huest.Utils.Constants;
import com.example.amankumar.huest.Utils.Utils;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class ChangePassword extends AppCompatActivity {
    Toolbar toolbar;
    EditText oldPasswordEdit, newPasswordEdit;
    String oldPassword,newPassword;
    Firebase ref;
    SharedPreferences sp;
    String encodedEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Change Password");
        Firebase.setAndroidContext(this);
        sp= PreferenceManager.getDefaultSharedPreferences(this);
        encodedEmail=sp.getString(Constants.CURRENT_USER_ENCODED_EMAIL, null);
        ref=new Firebase(Constants.FIREBASE_URL).child(encodedEmail);
        oldPasswordEdit = (EditText) findViewById(R.id.oldPassword);
        newPasswordEdit = (EditText) findViewById(R.id.newPassword);
    }

    public void changePassword(View view) {
        oldPassword = String.valueOf(oldPasswordEdit.getText());
        newPassword=String.valueOf(newPasswordEdit.getText());
        if(newPassword.length()<6){
            newPasswordEdit.setError("Should be more than 6 characters!");
            return;
        }
        ref.changePassword(Utils.decodeEmail(encodedEmail), oldPassword, newPassword, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Toast.makeText(ChangePassword.this, "Password Changed", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
               if (FirebaseError.INVALID_PASSWORD==firebaseError.getCode()){
                   oldPasswordEdit.setError("Invalid Password");
               }
            }
        });
    }

}
