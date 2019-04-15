package com.example.myfirstapp.MainActivity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.LoginActivity.LoginActivity;
import com.example.myfirstapp.MemberInformationActivity.MemberInformationActivity;

import org.jetbrains.annotations.NotNull;

public class SettingTabFragment extends Fragment {

    private Context context;
    private SharedPreferences sharedPreferences;

    private ConstraintLayout tvGotoLoginInfo;
    private TextView tvLoginID;
    private String token;
    private String userId;
    private boolean loggedIn;


    @NotNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_setting_tab, container, false);

        init(v);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        loginCheck();
        setLoginInfo();
    }
    @NotNull
    private void init(View v){
        context = getContext();
        sharedPreferences = context.getSharedPreferences("UserData",Context.MODE_PRIVATE);

        tvLoginID = v.findViewById(R.id.tvLoginID);
        tvGotoLoginInfo = v.findViewById(R.id.goto_login_info_button);

    }
    private void loginCheck(){
        token=sharedPreferences.getString("token","");
        userId = sharedPreferences.getString("user_id","");
        if(token.length()==0){
            tvLoginID.setText("로그인하세요.");
            loggedIn=false;
        }else{
            tvLoginID.setText(userId);
            loggedIn=true;
        }
    }
    private void setLoginInfo(){
        tvGotoLoginInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(loggedIn==false){//비로그인 상태
                    intent = new Intent(getContext(), LoginActivity.class);

                }else{//로그인한 상태
                    intent=new Intent(getContext(), MemberInformationActivity.class);
                    intent.putExtra("user_id",userId);
                }
                startActivity(intent);
            }
        });
    }
}
