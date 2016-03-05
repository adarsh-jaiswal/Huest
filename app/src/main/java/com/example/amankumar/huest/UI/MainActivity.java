package com.example.amankumar.huest.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.amankumar.huest.R;
import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button loginButton;
    Button signUpButton;
    EditText emailEditText, passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        Firebase.setAndroidContext(this);
        setSupportActionBar(toolbar);
        loginButton= (Button) findViewById(R.id.logInButton);
        signUpButton= (Button) findViewById(R.id.logInButton);
        emailEditText = (EditText) findViewById(R.id.userIdText);
        passwordEditText = (EditText) findViewById(R.id.Password);
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
        Intent intent=new Intent(this,GuestOrHostActivity.class);
        startActivity(intent);
    }

    public void loginButton(View view) {

    }
}
