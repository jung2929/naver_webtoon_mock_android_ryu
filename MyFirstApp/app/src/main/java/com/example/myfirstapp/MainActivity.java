package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    AccessTokenTracker accessTokenTracker;//facebook 로그인 토큰 트래커
    AccessToken accessToken;
    final int LAYOUT_NUM=5;
    View[] menuBar = new View[LAYOUT_NUM];
    int index=0;//changeLayout 함수 전용
    View searchButton;
    String days[] = {"월","화","수","목","금","토","일","신작","완결"};
    public static String WebtoonNames[]={"소녀의 세계", "복학왕", "데드라이프", "abcd", "abcde", "aaa"};
    public static int WebtoonThumnails[]={R.drawable.thumbnail_world_of_girl, R.drawable.thumbnail_king, R.drawable.thumbnail_dead_life
    ,R.drawable.thumbnail_not_loaded, R.drawable.thumbnail_not_loaded,R.drawable.thumbnail_not_loaded};
    GridView gridView[] = new GridView[days.length];
    GridThumnailAdapter gridThumbnailAdapter;

    ViewPager viewPager;
    TabLayout tabLayout;
    WebtoonDaysPageAdapter webtoonDaysPageAdapter;
    ArrayList<GridView> gridViewArrayList;

    ConstraintLayout tvGotoLogin;
    TextView tvLoginID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WebtoonSearch.class);
                startActivity(intent);
            }
        });

        gridThumbnailAdapter = new GridThumnailAdapter(this);
        gridThumbnailAdapter.addItem(WebtoonThumnails[0], WebtoonNames[0], "9.12","모랑지",false,false);
        gridThumbnailAdapter.addItem(WebtoonThumnails[1], WebtoonNames[1], "9.33","기안84",false,false);
        gridThumbnailAdapter.addItem(WebtoonThumnails[2], WebtoonNames[2], "9.44","모랑지",false,false);

        for(int i=0; i<days.length;i++) {
            gridView[i]=new GridView(this);
            gridView[i].setNumColumns(3);
            gridView[i].setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gridView[i].setBackgroundResource(R.color.border);

            gridView[i].setAdapter(gridThumbnailAdapter);
            int finalI = i;
            gridView[i].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ItemGridThumbnail item = (ItemGridThumbnail) gridView[finalI].getItemAtPosition(position);
                    if (item.isNone == true) return;
                    String webtoonName = item.getTitle();
                    Intent intent = new Intent(MainActivity.this, WebtoonList.class);
                    intent.putExtra("webtoonName", webtoonName);
                    startActivity(intent);
                }
            });
        }
        tabLayout = findViewById(R.id.tlWebtoonDays);
        for(int i=0; i<days.length; i++) {
           /* View customView = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
            TextView tv = customView.findViewById(R.id.tab_text);
            tv.setText(days[i]);*/
            tabLayout.addTab(tabLayout.newTab().setText(days[i]));
        }
        gridViewArrayList = new ArrayList<>();
        for(int i=0; i<days.length;i++){
        gridViewArrayList.add(gridView[i]);
        }
        webtoonDaysPageAdapter = new WebtoonDaysPageAdapter(gridViewArrayList,MainActivity.this);
        viewPager=findViewById(R.id.viewpager_webtoonlist);
        viewPager.setAdapter(webtoonDaysPageAdapter);

        menuBar[0] = findViewById(R.id.WebtoonTabScrollView);
        menuBar[1] = findViewById(R.id.SettingTabScrollView);
        menuBar[2] = findViewById(R.id.SettingTabScrollView);
        menuBar[3] = findViewById(R.id.SettingTabScrollView);
        menuBar[4] = findViewById(R.id.SettingTabScrollView);

        tvGotoLogin = findViewById(R.id.goto_login_button);
        tvGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        //페이스북 로그인
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
        tvLoginID = findViewById(R.id.tvLoginID);
        if (isLoggedIn == true) {
            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        System.out.println(object);
                        String birth = object.getString("birthday");
                        String name = object.getString("name");
                        tvLoginID.setText(name);
                        ;
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
        else {
            tvLoginID.setText("로그인하세요.");
        }
    }

    private void changeLayout(int n){
        index = n;
        for(int i=0; i<LAYOUT_NUM; i++){
            if(index == i) {
                menuBar[i].setVisibility(View.VISIBLE);
            }
            else {
                menuBar[i].setVisibility(View.INVISIBLE);
            }
        }
    }
    public void changeToMenuBarWebtoon(View v){ changeLayout(0); }
    public void changeToMenuBarBestChallenge(View v){
        changeLayout(1);
    }
    public void changeToMenuBarPlay(View v){
        changeLayout(2);
    }
    public void changeToMenuBarMymenu(View v){
        changeLayout(3);
    }
    public void changeToMenuBarSetting(View v){
        changeLayout(4);
    }
}
