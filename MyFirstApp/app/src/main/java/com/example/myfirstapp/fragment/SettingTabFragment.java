package com.example.myfirstapp.fragment;

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
import com.example.myfirstapp.activities.LoginActivity;
import com.example.myfirstapp.activities.MemberInformationActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;

public class SettingTabFragment extends Fragment {

    private Context context;

    private AccessTokenTracker accessTokenTracker;//facebook 로그인 토큰 트래커
    private AccessToken accessToken;

    private ConstraintLayout tvGotoLoginInfo;
    private TextView tvLoginID;
private String token;
private String userId;
    private boolean loggedIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_setting_tab, container, false);

        context = getContext();

        tvLoginID = v.findViewById(R.id.tvLoginID);
        tvGotoLoginInfo = v.findViewById(R.id.goto_login_info_button);


        //페이스북 로그인 하였는지?
        /*
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                accessToken=AccessToken.getCurrentAccessToken();
            }
        };
        accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        tvLoginID = v.findViewById(R.id.tvLoginID);
        if (isLoggedIn == true) {
            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                       // String birth = object.getString("birthday");
                       // String picture = "https://graph.facebook.com/"+object.getString("id")+"/picture?type=normal";
                        String name = object.getString("name");
                        tvLoginID.setText(name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            Bundle parameters = new Bundle();
            //https://developers.facebook.com/docs/facebook-login/permissions/ 권한
            //https://developers.facebook.com/docs/graph-api/using-graph-api/
            parameters.putString("fields", "id,email,name,birthday,picture");
            request.setParameters(parameters);
            request.executeAsync();
        }
        else {
            tvLoginID.setText("로그인하세요.");
        }
        */
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        //로그인 했는지 확인

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData",Context.MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        userId = sharedPreferences.getString("user_id","");
        if(token.length()==0){
            tvLoginID.setText("로그인하세요.");
            loggedIn=false;
        }else{
            tvLoginID.setText(userId);
            loggedIn=true;
        }

        //로그인 정보
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
