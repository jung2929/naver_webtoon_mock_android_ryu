package com.example.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        try{
        //    Thread.sleep(1000);
        }
        catch(Exception e) {
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
