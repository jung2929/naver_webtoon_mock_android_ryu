package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

public class FacebookLoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_birthday"));
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(FacebookLoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        finish();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(FacebookLoginActivity.this, exception.toString()+"", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
