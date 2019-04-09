package com.example.myfirstapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myfirstapp.R;
import com.example.myfirstapp.activities.LoginActivity;
import com.example.myfirstapp.activities.MainActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

public class SettingTabFragment extends Fragment {

    AccessTokenTracker accessTokenTracker;//facebook 로그인 토큰 트래커
    AccessToken accessToken;

    ConstraintLayout tvGotoLogin;
    TextView tvLoginID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_setting_tab, container, false);
        tvGotoLogin = v.findViewById(R.id.goto_login_button);
        tvGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //페이스북 로그인 하였는지?
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

        return v;
    }
}
