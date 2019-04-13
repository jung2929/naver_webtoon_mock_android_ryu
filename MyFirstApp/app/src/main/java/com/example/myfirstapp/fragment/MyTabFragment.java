package com.example.myfirstapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.GlobalApplication;
import com.example.myfirstapp.R;
import com.example.myfirstapp.activities.WebtoonContentsListActivity;
import com.example.myfirstapp.adapter.MyTabPagerAdapter;
import com.example.myfirstapp.adapter.WebtoonListAdapter;
import com.example.myfirstapp.entities.ResponseMyWebtoonListData;
import com.example.myfirstapp.entities.WebtoonListData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyTabFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyTabPagerAdapter myTabPagerAdapter;
    private ArrayList<ListView> list;
    private WebtoonListAdapter webtoonListAdapter[] =new WebtoonListAdapter[5];

    private TextView tvLoginID;
    private boolean loggedIn;
    private String token;
    private String userId;

    private Context context;

    private final int ATTENTION = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_tab, container, false);
        context = getContext();

        tvLoginID = v.findViewById(R.id.my_tab_login_id);


        //탭레이아웃 세팅
        tabLayout = v.findViewById(R.id.my_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("관심웹툰"));
        tabLayout.addTab(tabLayout.newTab().setText("최근 본 웹툰"));
        tabLayout.addTab(tabLayout.newTab().setText("임시저장 웹툰"));
        tabLayout.addTab(tabLayout.newTab().setText("보관함"));
        tabLayout.addTab(tabLayout.newTab().setText("결제내역"));

        //리스트뷰 세팅
        list= new ArrayList<>();
        for(int i=0; i<tabLayout.getTabCount(); i++){
            webtoonListAdapter[i] = new WebtoonListAdapter(getContext(), R.layout.item_list_webtoon_loose_form, WebtoonListAdapter.TYPE_LIST);
            list.add(new ListView(getContext()));
            list.get(i).setAdapter(webtoonListAdapter[i]);
            int finalI = i;
            list.get(i).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    WebtoonListData item = (WebtoonListData) list.get(finalI).getItemAtPosition(position);
                    if (item.isNone()) return;
                    Intent intent = new Intent(getApplicationContext(), WebtoonContentsListActivity.class);
                    intent.putExtra("comic", item);
                    startActivity(intent);
                }
            });
        }
        //관심웹툰 세팅
        SharedPreferences sharedPreferences = getContext()
                .getSharedPreferences(context.getString(R.string.sharedpreference_userdata_filename), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "오타남");
        Call<ResponseMyWebtoonListData> getAttentionList =
                GlobalApplication.softcomicsservice.getMyWebtoonList(token);
        getAttentionList.enqueue(new Callback<ResponseMyWebtoonListData>() {
            @Override
            public void onResponse(Call<ResponseMyWebtoonListData> call, Response<ResponseMyWebtoonListData> response) {
                if(response.isSuccessful()){
                    switch (response.body().getCode()) {
                        case 100://성공적
                        List<WebtoonListData> myList = response.body().getWebtoonList();
                        for (WebtoonListData e : myList) {
                            webtoonListAdapter[ATTENTION].addItem(e);
                        }
                        webtoonListAdapter[ATTENTION].notifyDataSetChanged();
                        break;
                        case 200://로그인필요
                           break;
                            default://?
                                Toast.makeText(getContext(), "코드 "+response.body().getCode()+" : 관심웹툰 불러오기 실패", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getContext(), "에러내용 : "+call.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMyWebtoonListData> call, Throwable t) {
                Toast.makeText(getContext(), "서버로부터 받아오지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        //뷰페이저 세팅
        viewPager = v.findViewById(R.id.my_tab_viewpager);
        myTabPagerAdapter = new MyTabPagerAdapter(list, getContext());
        viewPager.setAdapter(myTabPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tabLayout.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        //로그인 했는지 확인
        SharedPreferences sharedPreferences = context
                .getSharedPreferences(context.getString(R.string.sharedpreference_userdata_filename),Context.MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        userId = sharedPreferences.getString("user_id","");
        if(token.length()==0){
            tvLoginID.setText("로그인하세요.");
            tvLoginID.setTextColor(context.getResources().getColor(R.color.blackfontexplain));
            loggedIn=false;
        }else{
            tvLoginID.setText(userId+"님");
            tvLoginID.setTextColor(context.getResources().getColor(R.color.blackfont));
            loggedIn=true;
        }
    }
}
