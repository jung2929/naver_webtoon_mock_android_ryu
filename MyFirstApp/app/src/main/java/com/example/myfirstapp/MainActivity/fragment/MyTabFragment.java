package com.example.myfirstapp.MainActivity.fragment;

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
import com.example.myfirstapp.WebtoonContentsListActivity.WebtoonContentsListActivity;
import com.example.myfirstapp.MainActivity.adpater.MyTabPagerAdapter;
import com.example.myfirstapp.common.adapter.WebtoonListAdapter;
import com.example.myfirstapp.MainActivity.entities.ResponseMyWebtoonListData;
import com.example.myfirstapp.common.entities.WebtoonData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTabFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyTabPagerAdapter myTabPagerAdapter;
    private ArrayList<ListView> list;
    private WebtoonListAdapter webtoonListAdapter[] =new WebtoonListAdapter[5];
    private ArrayList<WebtoonData> webtoonDataList[] = new ArrayList[5];

    private TextView tvLoginID;
    private String token;
    private String userId;

    private Context context;
    private SharedPreferences sharedPreferences;

    private String tabNames[] = {"관심웹툰","최근 본 웹툰","임시저장 웹툰","보관함","결제내역"};
    private final int ATTENTION = 0;

    void init(View v){
        context = getContext();
        sharedPreferences = context
                .getSharedPreferences(context.getString(R.string.sharedpreference_userdata_filename),Context.MODE_PRIVATE);

        tvLoginID = v.findViewById(R.id.my_tab_login_id);
        tabLayout = v.findViewById(R.id.my_tab_layout);

        list = new ArrayList<>();
        for(int tabIndex = 0; tabIndex< tabNames.length; tabIndex++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabNames[tabIndex]));
            webtoonListAdapter[tabIndex] =
                    new WebtoonListAdapter(getContext(), webtoonDataList[tabIndex], R.layout.item_list_webtoon_loose_form, WebtoonListAdapter.TYPE_LIST);
            list.add(new ListView(getContext()));
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_tab, container, false);

        init(v);
        //리스트뷰 세팅
        for(int kindsOfTab=0; kindsOfTab<tabLayout.getTabCount(); kindsOfTab++){
            list.get(kindsOfTab).setAdapter(webtoonListAdapter[kindsOfTab]);
            int finalI = kindsOfTab;
            list.get(kindsOfTab).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    WebtoonData item = (WebtoonData) list.get(finalI).getItemAtPosition(position);
                    if (item.isNone()) return;
                    Intent intent = new Intent(getContext(), WebtoonContentsListActivity.class);
                    intent.putExtra("comic", item);
                    startActivity(intent);
                }
            });
        }
        //관심웹툰 세팅
        SharedPreferences sharedPreferences = getContext()
                .getSharedPreferences(context.getString(R.string.sharedpreference_userdata_filename), Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Call<ResponseMyWebtoonListData> getAttentionList =
                GlobalApplication.softcomicsService.getMyWebtoonList(token);
        getAttentionList.enqueue(new Callback<ResponseMyWebtoonListData>() {
            @Override
            public void onResponse(Call<ResponseMyWebtoonListData> call, Response<ResponseMyWebtoonListData> response) {
                if(response.isSuccessful()){
                    switch (response.body().getCode()) {
                        case 100://성공적
                        List<WebtoonData> myList = response.body().getWebtoonList();
                        webtoonDataList[ATTENTION] = (ArrayList)myList;
                        webtoonListAdapter[ATTENTION].notifyDataSetChanged();
                        break;
                        case 200://로그인필요
                           break;
                            default://?
                     }
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
        LoginCheck();

    }
    void LoginCheck(){
        token=sharedPreferences.getString("token","");
        userId = sharedPreferences.getString("user_id","");
        if(token.length()==0){
            tvLoginID.setText("로그인하세요.");
            tvLoginID.setTextColor(context.getResources().getColor(R.color.blackfontexplain));
        }else{
            tvLoginID.setText(userId+"님");
            tvLoginID.setTextColor(context.getResources().getColor(R.color.blackfont));
        }
    }
}
