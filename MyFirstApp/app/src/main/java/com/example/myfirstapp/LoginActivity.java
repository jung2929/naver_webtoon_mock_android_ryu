package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONObject;

import java.util.Arrays;

import static com.nhn.android.naverlogin.OAuthLogin.mOAuthLoginHandler;

public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    private OAuthLoginButton mOAuthLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if(isLoggedIn == true) {
            System.out.println(accessToken.getUserId());
            System.out.println(accessToken.getToken());
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_birthday"));
            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        String birth = object.getString("birthday");
                        String name = object.getString("name");
                        TextView tvName = findViewById(R.id.facebook_name);
                        tvName.setText("Facebook 이름 : "+name +"\n"+"Facebook 생일 : "+birth);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            Bundle parameters = new Bundle();
            //https://developers.facebook.com/docs/facebook-login/permissions/ 권한
            //https://developers.facebook.com/docs/graph-api/using-graph-api/
            parameters.putString("fields", "id,email,name,birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.toString()+"", Toast.LENGTH_SHORT).show();
                        // App code
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
                Intent intent = new Intent(LoginActivity.this, NaverLogin.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
