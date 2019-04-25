package com.example.myfirstapp.SignUp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfirstapp.Singleton;
import com.example.myfirstapp.R;
import com.example.myfirstapp.SignUp.Entities.ResponseSignUpData;
import com.example.myfirstapp.SignUp.Entities.RequestSignUpData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private Button btnSignUp;
    private EditText etSignUpInput[] = new EditText[5];
    private RequestSignUpData requestSignUpData;
    private final int INFO_NUM = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Singleton.isStartActivity = false;

        init();
        setSignUpButton();
    }

    private void init() {
        etSignUpInput[0] = findViewById(R.id.signup_id_input);
        etSignUpInput[1] = findViewById(R.id.signup_pw_input);
        etSignUpInput[2] = findViewById(R.id.signup_subpw_input);
        etSignUpInput[3] = findViewById(R.id.signup_mail_input);
        etSignUpInput[4] = findViewById(R.id.signup_tel_input);
        btnSignUp = findViewById(R.id.softcomics_signup_button);

    }

    private void setSignUpButton() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str[] = new String[INFO_NUM];
                for (int info = 0; info < INFO_NUM; info++) {
                    str[info] = etSignUpInput[info].getText().toString();
                    if (str[info].equals("")) {
                        Toast.makeText(SignUpActivity.this, "빈 칸을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        etSignUpInput[info].requestFocus();
                        return;
                    }
                }
                requestSignUpData = new RequestSignUpData(
                        str[0], str[1], str[2], str[3], str[4]);
                Call<ResponseSignUpData> data = Singleton.softcomicsService.signUp(requestSignUpData);
                data.enqueue(new Callback<ResponseSignUpData>() {
                    @Override
                    public void onResponse(Call<ResponseSignUpData> call, Response<ResponseSignUpData> response) {
                        if (response.isSuccessful()) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                            alert.setTitle("회원가입");
                            switch (response.body().getCode()) {
                                case 100://회원가입 성공
                                    alert.setMessage("회원가입이 완료되었습니다.\n로그인해주세요.");
                                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    });
                                    alert.show();
                                    break;
                                case 200://아이디 중복
                                    alert.setMessage("이미 존재하는 아이디입니다.");
                                    alert.setPositiveButton("확인", null);
                                    alert.show();
                                    break;
                                case 201://비밀번호 비일치
                                    alert.setMessage("비밀번호 재확인이 틀렸습니다.");
                                    alert.setPositiveButton("확인", null);
                                    alert.show();
                                    break;
                                case 202://이메일 중복
                                    alert.setMessage("이미 존재하는 email입니다.");
                                    alert.setPositiveButton("확인", null);
                                    alert.show();
                                    break;
                                case 203://이메일 형식 틀림
                                    alert.setMessage("이메일 형식이 잘못되었습니다.");
                                    alert.setPositiveButton("확인", null);
                                    alert.show();
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseSignUpData> call, Throwable t) {
                        Toast.makeText(SignUpActivity.this, "에러 : " + t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
