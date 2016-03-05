package com.example.amankumar.huest.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.amankumar.huest.R;
import com.firebase.client.Firebase;

public class HostSignUpActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText hostFirstName, hostLastName, hostEmail, hostMobile, hostPassword;

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
        hostPassword= (EditText) findViewById(R.id.Password);
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
     /*   String firstName= String.valueOf(hostFirstName.getText());
        String lastName= String.valueOf(hostLastName.getText());
        String email= String.valueOf(hostEmail.getText());
        String number= String.valueOf(hostMobile.getText());
        String password= String.valueOf(hostPassword.getText());
        Firebase ref=new Firebase(Constants.FIREBASE_URL);
        HuestPeople people=new HuestPeople(firstName,lastName,email,number,password);
        ref.child("host").setValue(people);*/
        Intent intent = new Intent(this, HostActivity.class);
        startActivity(intent);
    }
}
