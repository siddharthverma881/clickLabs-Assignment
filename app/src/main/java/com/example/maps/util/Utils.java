package com.example.maps.util;

import android.content.Context;
import android.widget.Toast;

public class Utils {

    public void showToast(Context context , String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
