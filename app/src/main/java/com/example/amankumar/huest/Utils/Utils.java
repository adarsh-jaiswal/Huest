package com.example.amankumar.huest.Utils;

import android.content.Context;

import java.text.SimpleDateFormat;

/**
 * Created by AmanKumar on 3/5/2016.
 */
public class Utils {
    public static final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private Context mContext=null;

    public Utils(Context mContext) {
        this.mContext = mContext;
    }
}
