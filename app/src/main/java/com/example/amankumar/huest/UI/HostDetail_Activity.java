package com.example.amankumar.huest.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.amankumar.huest.R;
import com.example.amankumar.huest.Utils.Constants;
import com.example.amankumar.huest.model.HuestPeople;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class HostDetail_Activity extends AppCompatActivity {

    TextView firstName,email;
    Toolbar toolbar;
    String pushId;
    Firebase ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_detail_);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Huest");
        Firebase.setAndroidContext(this);
        firstName= (TextView) findViewById(R.id.host_detail_first_name);
        email= (TextView) findViewById(R.id.host_detail_email);
        Intent intent=getIntent();
        pushId=intent.getStringExtra("pushId");
        ref=new Firebase(Constants.FIREBASE_URL).child("host").child(pushId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HuestPeople people=dataSnapshot.getValue(HuestPeople.class);
                firstName.setText(people.getFirstName()+" "+people.getLastName());
                email.setText(people.getEmail());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_host_detail_, menu);
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
}
