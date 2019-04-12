package com.example.myfirstapp.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.NonSwipeableViewPager;
import com.example.myfirstapp.adapter.MainPagerAdapter;
import com.example.myfirstapp.R;
import com.example.myfirstapp.entities.AirKoreaData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    private class MenuTabItem{
        int unClickedImage;
        int clickedImage;
        String text;

        public MenuTabItem(int unClickedImage, int clickedImage, String text) {
            this.unClickedImage = unClickedImage;
            this.clickedImage = clickedImage;
            this.text = text;
        }

        public int getUnClickedImage() {
            return unClickedImage;
        }

        public int getClickedImage() {
            return clickedImage;
        }

        public String getText() {
            return text;
        }
    }
    MenuTabItem menuTabItem[]=new MenuTabItem[5];

    Retrofit airRetrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //메뉴바 설정
        menuTabItem[0]=new MenuTabItem(R.drawable.ic_clicked_webtoon,R.drawable.ic_clicked_webtoon,"웹툰");
        menuTabItem[1]=new MenuTabItem(R.drawable.ic_unclicked_best_challenge,R.drawable.ic_clicked_webtoon,"베스트도전");
        menuTabItem[2]=new MenuTabItem(R.drawable.ic_unclicked_play,R.drawable.ic_clicked_webtoon,"PLAY");
        menuTabItem[3]=new MenuTabItem(R.drawable.ic_unclicked_mymenu,R.drawable.ic_clicked_webtoon,"MY");
        menuTabItem[4]=new MenuTabItem(R.drawable.ic_unclicked_setting,R.drawable.ic_clicked_webtoon,"설정");
        final TabLayout menuTabLayout= findViewById(R.id.main_menu_tab);
        final NonSwipeableViewPager mainViewPager = findViewById(R.id.main_viewpager);
        final MainPagerAdapter mainPagerAdapter = new MainPagerAdapter
                (getSupportFragmentManager(), menuTabLayout.getTabCount(), this);
        menuTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        for(int i=0; i<5; i++) {//탭레이아웃 꾸미기
            View t = LayoutInflater.from(this).inflate(R.layout.item_menubar_tab, menuTabLayout, false);
            TextView tv = t.findViewById(R.id.tab_text);
            tv.setText(menuTabItem[i].getText());
            tv.setTextColor(getResources().getColor(R.color.unclickedMenuBar));
            ImageView iv=t.findViewById(R.id.tab_image);
            iv.setImageResource(menuTabItem[i].getUnClickedImage());
            menuTabLayout.getTabAt(i).setCustomView(t);
//            menuTabLayout.addTab(menuTabLayout.newTab().setCustomView(t));
        }
        mainViewPager.setAdapter(mainPagerAdapter);
        menuTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int i=tab.getPosition();
                mainViewPager.setCurrentItem(i);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        /*------retrofit 사용------------------
        airRetrofit = new Retrofit.Builder()
                .baseUrl("http://openapi.airkorea.or.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
                */
    }
    @Override
    protected void onResume() {
        super.onResume();

        /*//대기 상황
        AirService airService = airRetrofit.create(AirService.class);
        Call<AirKoreaData> airState = airService.getAir("종로구");

        airState.enqueue(new Callback<AirKoreaData>() {
            @Override
            public void onResponse(Call<AirKoreaData> call, Response<AirKoreaData> response) {
                AirKoreaData a = response.body();
                try {
                    Toast.makeText(MainActivity.this, a.getWeatherList().get(0).getDataTime() + "시 " + a.getParm().getStationName() + "의 일산화탄소농도 : " + a.getWeatherList().get(0).getCoValue(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<AirKoreaData> call, Throwable t) {
                System.out.println("에러내용 : "+t.toString());
            }
        });*/

    }
    public interface  AirService{
        @GET("openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?dataTerm=month&pageNo=1&numOfRows=10&ServiceKey=IbUJs05q2%2B93Wy%2BKbqxYhI%2BnnNFOLoyRB8tKb66rp95UVccJ5ZTgRAX%2BV0ckS84k4bufsPzg7SRCwqGcuqWHnw%3D%3D&_returnType=json")
        Call<AirKoreaData> getAir(@Query("stationName") String stationName);
    }
}
