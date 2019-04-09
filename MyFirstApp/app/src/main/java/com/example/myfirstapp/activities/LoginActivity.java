package com.example.myfirstapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myfirstapp.R;
import com.example.myfirstapp.enitites.SoftComicsMemberData;
import com.example.myfirstapp.enitites.AirKoreaData;
import com.facebook.AccessToken;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.nhn.android.naverlogin.OAuthLogin.mOAuthLoginHandler;

public class LoginActivity extends AppCompatActivity {

    com.facebook.login.widget.LoginButton btnFacebookLogin;
    private OAuthLoginButton mOAuthLoginButton;
    private Retrofit softRetrofit;
    private SoftComicsMemberData softComicsMemberData;
    public interface  softcomicsService{
        @GET("user")
       // Call<SoftComicsMemberData> signUp(@Body String id, @Body String pw, @Body String subpw, @Body String email, @Body String tel);
        Call<SoftComicsMemberData> signUp(@Body SoftComicsMemberData memberData);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //softcomics.co.kr
        softRetrofit = new Retrofit.Builder()
                .baseUrl("softcomics.co.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        softcomicsService softcomicsservice=softRetrofit.create(softcomicsService.class);
        softComicsMemberData = new SoftComicsMemberData("myid","pw","pw","email@gmail.com","00012341234");
        Call<SoftComicsMemberData> data = softcomicsservice.signUp(softComicsMemberData);
        data.enqueue(new Callback<SoftComicsMemberData>() {
            @Override
            public void onResponse(Call<SoftComicsMemberData> call, Response<SoftComicsMemberData> response) {
                if(response.isSuccessful())
                    Toast.makeText(LoginActivity.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
                else {
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<SoftComicsMemberData> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.toString()+"", Toast.LENGTH_SHORT).show();
            }
        });
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
