package com.example.amankumar.huest.Adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.amankumar.huest.R;
import com.example.amankumar.huest.model.HuestPeople;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

/**
 * Created by AmanKumar on 3/8/2016.
 */
public class ActiveListAdapter extends FirebaseListAdapter<HuestPeople> {
    public ActiveListAdapter(Activity activity, Class<HuestPeople> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
        this.mActivity = activity;
    }

    @Override
    protected void populateView(View v, HuestPeople model) {
        TextView firstName = (TextView) v.findViewById(R.id.listView_FirstName);
        TextView email = (TextView) v.findViewById(R.id.listView_email);

        firstName.setText(model.getFirstName());
        email.setText(model.getEmail());
    }
}
