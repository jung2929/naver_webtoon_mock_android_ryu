package com.example.myfirstapp.activities;

import android.content.Intent;
import android.service.autofill.DateValueSanitizer;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myfirstapp.adapter.MainPagerAdapter;
import com.example.myfirstapp.adapter.WebtoonListAdapter;
import com.example.myfirstapp.R;
import com.example.myfirstapp.adapter.WebtoonDaysPageAdapter;
import com.example.myfirstapp.enitites.AirKoreaData;
import com.example.myfirstapp.enitites.WebtoonData;
import com.example.myfirstapp.enitites.WebtoonListData;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.example.myfirstapp.GlobalApplication.webtoonList;

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

        menuTabItem[0]=new MenuTabItem(R.drawable.ic_clicked_webtoon,R.drawable.ic_clicked_webtoon,"웹툰");
        menuTabItem[1]=new MenuTabItem(R.drawable.ic_unclicked_best_challenge,R.drawable.ic_clicked_webtoon,"베스트도전");
        menuTabItem[2]=new MenuTabItem(R.drawable.ic_unclicked_play,R.drawable.ic_clicked_webtoon,"PLAY");
        menuTabItem[3]=new MenuTabItem(R.drawable.ic_unclicked_mymenu,R.drawable.ic_clicked_webtoon,"MY");
        menuTabItem[4]=new MenuTabItem(R.drawable.ic_unclicked_setting,R.drawable.ic_clicked_webtoon,"설정");

        final TabLayout menuTabLayout= findViewById(R.id.main_menu_tab);
        final ViewPager mainViewPager = findViewById(R.id.main_viewpager);
        final MainPagerAdapter mainPagerAdapter = new MainPagerAdapter
                (getSupportFragmentManager(), menuTabLayout.getTabCount());
        menuTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        for(int i=0; i<5; i++) {
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
        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                menuTabLayout.setScrollPosition(i,0f,true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });




        //------retrofit 사용------------------
        /*Gson gson = new GsonBuilder()
                .setLenient()
                .create();*/
        airRetrofit = new Retrofit.Builder()
                .baseUrl("http://openapi.airkorea.or.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);

        String str = "veev23";
        Call<List<Repo>> repos = service.listRepos(str);

        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                for(int i=0; i<response.body().size();i++){
                    System.out.println(i+"번아이디 : "+response.body().get(i).getId());
                    System.out.println(i+"번이름 : "+response.body().get(i).getName());
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "에러:"+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

        //대기 상황
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
        });

    }

    public interface GitHubService {
        @GET("/users/{user}/repos")
        Call<List<Repo>> listRepos(@Path("user") String user);
    }
    public interface  AirService{
        @GET("openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?dataTerm=month&pageNo=1&numOfRows=10&ServiceKey=IbUJs05q2%2B93Wy%2BKbqxYhI%2BnnNFOLoyRB8tKb66rp95UVccJ5ZTgRAX%2BV0ckS84k4bufsPzg7SRCwqGcuqWHnw%3D%3D&_returnType=json")
        Call<AirKoreaData> getAir(@Query("stationName") String stationName);
    }
    private class Repo{
        String id;
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
