package com.example.amankumar.huest.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.amankumar.huest.Adapter.ActiveListAdapter;
import com.example.amankumar.huest.R;
import com.example.amankumar.huest.Utils.Constants;
import com.example.amankumar.huest.model.HuestPeople;
import com.firebase.client.Firebase;

public class GuestActivity extends AppCompatActivity {
    ListView listView;
    Toolbar toolbar;
    ActiveListAdapter mActiveListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Huest");
        Firebase.setAndroidContext(this);
        listView= (ListView) findViewById(R.id.ListView);
        Firebase ref=new Firebase(Constants.FIREBASE_URL).child("host");
        mActiveListAdapter=new ActiveListAdapter(this, HuestPeople.class,R.layout.list_view,ref);
        listView.setAdapter(mActiveListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HuestPeople people=mActiveListAdapter.getItem(position);
                if(people!=null){
                    String listId=mActiveListAdapter.getRef(position).getKey();
                    Intent intent=new Intent(view.getContext(),HostDetail_Activity.class);
                    intent.putExtra("pushId",listId);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guest, menu);
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
