package com.example.myfirstapp.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfirstapp.Singleton;
import com.example.myfirstapp.R;
import com.example.myfirstapp.SignUp.SignUpActivity;
import com.example.myfirstapp.Login.Entities.ResponseLoginData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Context context;
    private final int ID_INPUT=0;
    private final int PW_INPUT=1;
    private Button btnSignUp;
    private Button btnLogin;
    private EditText etLoginInput[]=new EditText[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context=LoginActivity.this;

        init();
        loginSetting();
        toSignUpActivitySetting();

    }
    void init(){
        etLoginInput[ID_INPUT]=findViewById(R.id.login_id_input);
        etLoginInput[PW_INPUT]=findViewById(R.id.login_pw_input);
        btnSignUp=findViewById(R.id.go_to_signup_activity_button);
        btnLogin=findViewById(R.id.softcomics_login_button);
    }
    void loginSetting(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput[]=new String[2];
                for(int i=0; i<2; i++) userInput[i]=new String(etLoginInput[i].getText().toString());
                if(userInput[ID_INPUT].equals("")){
                    Toast.makeText(context, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }else if(userInput[PW_INPUT].equals("")){
                    Toast.makeText(context, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Call<ResponseLoginData> loginCall= Singleton.softcomicsService.login(userInput[ID_INPUT],userInput[PW_INPUT]);
                loginCall.enqueue(new Callback<ResponseLoginData>() {
                    @Override
                    public void onResponse(Call<ResponseLoginData> call, Response<ResponseLoginData> response) {
                        if(response.isSuccessful()){
                            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                            alert.setTitle("로그인");
                            alert.setPositiveButton("확인",null);
                            switch (response.body().getCode()){
                                case 100://로그인 성공
                                    Toast.makeText(LoginActivity.this, userInput[ID_INPUT]+"님 환영합니다.", Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedPreferences =
                                            context.getSharedPreferences(getResources().getString(R.string.sharedpreference_userdata_filename), Context.MODE_PRIVATE);
                                    SharedPreferences.Editor edit = sharedPreferences.edit();
                                    edit.putString("user_id",userInput[ID_INPUT]);
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
    }
    void toSignUpActivitySetting(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
