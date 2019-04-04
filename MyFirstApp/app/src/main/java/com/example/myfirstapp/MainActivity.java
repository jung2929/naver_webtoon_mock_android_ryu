package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final int LAYOUT_NUM=5;
    View[] menuBar = new View[LAYOUT_NUM];
    int index=0;//changeLayout 함수 전용
    View searchButton;
    String days[] = {"월","화","수","목","금","토","일","신작","완결"};
    public static String WebtoonNames[]={"소녀의 세계", "복학왕", "데드라이프", "abcd", "abcde", "aaa"};
    public static int WebtoonThumnails[]={R.drawable.thumbnail_world_of_girl, R.drawable.thumbnail_king, R.drawable.thumbnail_dead_life
    ,R.drawable.thumbnail_not_loaded, R.drawable.thumbnail_not_loaded,R.drawable.thumbnail_not_loaded};
    GridView gridView;
    GridThumnailAdapter gridThumbnailAdapter;

    ViewPager viewPager;
    TabLayout tabLayout;
    WebtoonDaysPageAdapter webtoonDaysPageAdapter;
    ArrayList<GridView> gridList;

    TextView tvGotoLogin;

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

        gridView = findViewById(R.id.webtoonlist_main_grid);
        gridThumbnailAdapter = new GridThumnailAdapter(this);
        gridThumbnailAdapter.addItem(WebtoonThumnails[0], WebtoonNames[0], "9.12","모랑지",false,false);
        gridThumbnailAdapter.addItem(WebtoonThumnails[1], WebtoonNames[1], "9.33","기안84",false,false);
        gridThumbnailAdapter.addItem(WebtoonThumnails[2], WebtoonNames[2], "9.44","모랑지",false,false);
        gridThumbnailAdapter.addItem(R.drawable.thumbnail_dead_life, WebtoonNames[2], "9.44","모랑지",false,false);
        gridThumbnailAdapter.addItem(R.drawable.thumbnail_dead_life, WebtoonNames[2], "9.44","모랑지",false,false);
        gridThumbnailAdapter.addItem(R.drawable.thumbnail_world_of_girl, WebtoonNames[0], "9.12","모랑지",false,false);
        gridThumbnailAdapter.addItem(R.drawable.thumbnail_king, WebtoonNames[1], "9.33","기안84",false,false);
        gridThumbnailAdapter.addItem(R.drawable.thumbnail_world_of_girl, WebtoonNames[0], "9.12","모랑지",false,false);
        gridThumbnailAdapter.addItem(R.drawable.thumbnail_king, WebtoonNames[1], "9.33","기안84",false,false);
        gridThumbnailAdapter.addItem(R.drawable.thumbnail_world_of_girl, WebtoonNames[0], "9.12","모랑지",false,false);
        gridThumbnailAdapter.addItem(R.drawable.thumbnail_king, WebtoonNames[1], "9.33","기안84",false,false);
        gridThumbnailAdapter.addItem(R.drawable.thumbnail_dead_life, WebtoonNames[2], "9.44","모랑지",false,false);
        gridThumbnailAdapter.addItem(R.drawable.thumbnail_world_of_girl, WebtoonNames[0], "9.12","모랑지",false,false);

        gridView.setAdapter(gridThumbnailAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemGridThumbnail item =(ItemGridThumbnail)gridView.getItemAtPosition(position);
                if(item.isNone==true) return;
                String webtoonName=item.getTitle();
                Intent intent = new Intent(MainActivity.this, WebtoonList.class);
                intent.putExtra("webtoonName", webtoonName);
                startActivity(intent);
            }
        });

        tabLayout = findViewById(R.id.tlWebtoonDays);
        for(int i=0; i<days.length; i++)
            tabLayout.addTab(tabLayout.newTab().setText(days[i]));
   /*     gridList = new ArrayList<>();
        gridList.add(gridView);
        webtoonDaysPageAdapter = new WebtoonDaysPageAdapter(gridList,MainActivity.this);
        viewPager=findViewById(R.id.viewpager_webtoonlist);
        viewPager.setAdapter(webtoonDaysPageAdapter);
*/
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
