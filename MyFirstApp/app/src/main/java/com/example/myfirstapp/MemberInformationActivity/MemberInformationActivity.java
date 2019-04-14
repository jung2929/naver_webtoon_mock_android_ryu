package com.example.myfirstapp.MemberInformationActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.GlobalApplication;
import com.example.myfirstapp.R;
import com.example.myfirstapp.MemberInformationActivity.entities.ResponseWithdrawalData;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberInformationActivity extends AppCompatActivity {

    private Context context;
    private Button btnWithdrawal;
    private Button btnLogout;
    private String userId;
    private TextView tvUserId;

    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_information);

        context=MemberInformationActivity.this;
        //회원 정보 세팅
        Intent intentGet = getIntent();
        userId = intentGet.getExtras().getString("user_id");
        tvUserId=findViewById(R.id.member_information_id);
        tvUserId.setText(userId);

        SharedPreferences sharedPreferences = context
                .getSharedPreferences(context.getString(R.string.sharedpreference_userdata_filename), Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
        SharedPreferences.Editor edit = sharedPreferences.edit();

        //회원 탈퇴 버튼
        btnWithdrawal = findViewById(R.id.softcomics_withdrawal_button);
        btnWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder msg = new AlertDialog.Builder(MemberInformationActivity.this);
                msg.setTitle("회원 탈퇴");
                msg.setMessage("정말로 탈퇴하시겠습니까?");
                msg.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                msg.setNegativeButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder pwMsg = new AlertDialog.Builder(MemberInformationActivity.this);
                        pwMsg.setTitle("정보 입력");
                        pwMsg.setMessage("정보확인을 위해 비밀번호를 입력해 주세요.");
                        EditText etPassword = new EditText(MemberInformationActivity.this);
                        pwMsg.setView(etPassword);
                        pwMsg.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        pwMsg.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //정보 가져옴
                                String pw = etPassword.getText().toString();
                                RequestBody body =
                                        RequestBody.create(MediaType.parse("text/plain"), pw);
                                Call<ResponseWithdrawalData> withdrawal =//서버로부터 회원탈퇴 요구 보냄
                                        GlobalApplication.softcomicsService.withdrawal(
                                                body, token);
                                withdrawal.enqueue(new Callback<ResponseWithdrawalData>() {
                                    @Override
                                    public void onResponse(Call<ResponseWithdrawalData> call, Response<ResponseWithdrawalData> response) {
                                        if(response.isSuccessful()){
                                            switch (response.body().getCode()){
                                                case 100://회원탈퇴 완료
                                                    Toast.makeText(MemberInformationActivity.this, "회원탈퇴가 정상적으로 이루어졌습니다.", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                    break;
                                                case 200://로그인 필요
                                                    Toast.makeText(MemberInformationActivity.this, "ErrorCode 200 : 로그인 필요", Toast.LENGTH_SHORT).show();
                                                break;
                                                case 201://비밀번호 틀림
                                                    Toast.makeText(MemberInformationActivity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                                break;
                                                case 205://존재하지않는 토큰
                                                    Toast.makeText(MemberInformationActivity.this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                                    edit.putString("user_id", "");
                                                    edit.putString("token", "");
                                                    edit.commit();
                                                    finish();
                                                    break;
                                            }
                                        }
                                        else{
                                            Toast.makeText(MemberInformationActivity.this, "회원탈퇴 에러", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseWithdrawalData> call, Throwable t) {
                                        Toast.makeText(MemberInformationActivity.this, "전송 실패", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                        pwMsg.show();
                    }
                });
                msg.show();
            }
        });

        //로그아웃 버튼
        btnLogout = findViewById(R.id.softcomics_logout_button);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.putString("user_id", "");
                edit.putString("token", "");
                edit.commit();
                finish();
            }
        });
    }
}
