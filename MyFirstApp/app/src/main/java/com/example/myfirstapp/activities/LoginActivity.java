package com.example.myfirstapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myfirstapp.R;
import com.example.myfirstapp.entities.SignUpResponseData;
import com.example.myfirstapp.entities.SoftComicsMemberData;
import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;


import okhttp3.Request;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.nhn.android.naverlogin.OAuthLogin.mOAuthLoginHandler;

public class LoginActivity extends AppCompatActivity {

    com.facebook.login.widget.LoginButton btnFacebookLogin;
    private OAuthLoginButton mOAuthLoginButton;
    private Retrofit softRetrofit;
    private SoftComicsMemberData softComicsMemberData;
    private OkHttpClient client = new OkHttpClient.Builder()
            .build();
    public void requestPost(){

        //Request Body에 서버에 보낼 데이터 작성
        RequestBody requestBody = new FormBody.Builder().add("id", "id").add("pw", "pw").add("subpw", "pw").add("email", "pw@gmail.com").add("tel", "01111123123").build();

        //작성한 Request Body와 데이터를 보낼 url을 Request에 붙임
        Request request = new Request.Builder().url("http://softcomics.co.kr/user").post(requestBody).build();

        //request를 Client에 세팅하고 Server로 부터 온 Response를 처리할 Callback 작성
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                System.out.println(e.toString()+" ok 에러");
                Toast.makeText(LoginActivity.this, "failure", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                System.out.println("성공 :"+response.body().string());
            }
        });
    }
    public interface  softcomicsService {
        @POST("user")
        Call<SignUpResponseData> signUp(@Body SoftComicsMemberData memberData);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //softcomics.co.kr
         Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        softRetrofit = new Retrofit.Builder()
                .baseUrl("http://softcomics.co.kr/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        softcomicsService softcomicsservice=softRetrofit.create(softcomicsService.class);
        softComicsMemberData = new SoftComicsMemberData("myid","pw","pw","email@gmail.com","00012341234");
        Call<SignUpResponseData> data = softcomicsservice.signUp(softComicsMemberData);

        requestPost();

        data.enqueue(new Callback<SignUpResponseData>() {
            @Override
            public void onResponse(Call<SignUpResponseData> call, Response<SignUpResponseData> response) {
                if(response.isSuccessful())
                    Toast.makeText(LoginActivity.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
                else {
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<SignUpResponseData> call, Throwable t) {
                System.out.println("에러 : " + t.toString());
                Toast.makeText(LoginActivity.this, "에러 : " + t.toString(), Toast.LENGTH_SHORT).show();
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
