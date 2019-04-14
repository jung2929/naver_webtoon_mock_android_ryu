package com.example.myfirstapp.SplashActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.myfirstapp.MainActivity.MainActivity;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
