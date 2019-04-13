package com.example.myfirstapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfirstapp.GlobalApplication;
import com.example.myfirstapp.R;
import com.example.myfirstapp.entities.ResponseLoginData;
import com.facebook.AccessToken;
import com.facebook.login.Login;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nhn.android.naverlogin.OAuthLogin.mOAuthLoginHandler;

public class LoginActivity extends AppCompatActivity {

    private Context context;
    private final int ID_INPUT=0;
    private final int PW_INPUT=1;
    private Button btnSignUp;
    private Button btnLogin;
    private EditText etLoginInput[]=new EditText[2];
    private com.facebook.login.widget.LoginButton btnFacebookLogin;
    private OAuthLoginButton mOAuthLoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context=LoginActivity.this;

        //softcomics login
        etLoginInput[ID_INPUT]=findViewById(R.id.login_id_input);
        etLoginInput[PW_INPUT]=findViewById(R.id.login_pw_input);
        btnLogin=findViewById(R.id.softcomics_login_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str[]=new String[2];
                for(int i=0; i<2; i++) str[i]=new String(etLoginInput[i].getText().toString());
                if(str[ID_INPUT].equals("")){
                    Toast.makeText(context, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }else if(str[PW_INPUT].equals("")){
                    Toast.makeText(context, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Call<ResponseLoginData> loginCall= GlobalApplication.softcomicsservice.login(str[ID_INPUT],str[PW_INPUT]);
                loginCall.enqueue(new Callback<ResponseLoginData>() {
                    @Override
                    public void onResponse(Call<ResponseLoginData> call, Response<ResponseLoginData> response) {
                        if(response.isSuccessful()){
                            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                            alert.setTitle("로그인");
                            alert.setPositiveButton("확인",null);
                            switch (response.body().getCode()){
                                case 100://로그인 성공
                                    Toast.makeText(LoginActivity.this, str[ID_INPUT]+"님 환영합니다.", Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor edit = sharedPreferences.edit();
                                    edit.putString("user_id",str[ID_INPUT]);
                                    edit.putString("token", response.body().getResult().getJwt());
                                    edit.commit();
                                    finish();
                                    break;
                                case 200://아이디없음
                                    alert.setMessage("존재하지 않는 아이디입니다.");
                                    alert.show();
                                    etLoginInput[PW_INPUT].setText("");
                                 break;
                                case 201://비밀번호틀림
                                    alert.setMessage("틀린 비밀번호입니다.");
                                    alert.show();
                                    etLoginInput[PW_INPUT].setText("");
                                    break;
                            }
                        }
                        else{
                            Toast.makeText(context, "에러내용 : "+call.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseLoginData> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "에러 : "+t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //SignUpActivity 버튼
        btnSignUp=findViewById(R.id.go_to_signup_activity_button);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
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
