package com.example.myfirstapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.myfirstapp.R;
import com.facebook.AccessToken;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import static com.nhn.android.naverlogin.OAuthLogin.mOAuthLoginHandler;

public class LoginActivity extends AppCompatActivity {

    com.facebook.login.widget.LoginButton btnFacebookLogin;
    private OAuthLoginButton mOAuthLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//페이스북

        btnFacebookLogin=findViewById(R.id.login_button_facebook);
        btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                if(isLoggedIn==false) {
                    Intent intent = new Intent(LoginActivity.this, FacebookLoginActivity.class);
                    startActivity(intent);
                }
            }
        });
//카카오
        View kakaoLoginButton = findViewById(R.id.com_kakao_login);
        kakaoLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this, KaKaoLoginActivity.class);
                startActivity(intent);
            }
        });
//네이버
        mOAuthLoginButton = findViewById(R.id.buttonOAuthLoginImg);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        mOAuthLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, NaverLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
