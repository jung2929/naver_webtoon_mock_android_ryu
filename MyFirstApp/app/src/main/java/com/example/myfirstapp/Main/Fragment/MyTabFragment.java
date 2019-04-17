package com.example.myfirstapp.Main.Fragment;

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

import com.example.myfirstapp.Singleton;
import com.example.myfirstapp.R;
import com.example.myfirstapp.WebtoonContentsList.WebtoonContentsListActivity;
import com.example.myfirstapp.Main.Adpater.MyTabPagerAdapter;
import com.example.myfirstapp.common.Adapter.WebtoonListAdapter;
import com.example.myfirstapp.Main.Entities.ResponseMyWebtoonListData;
import com.example.myfirstapp.common.Entities.WebtoonData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTabFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyTabPagerAdapter myTabPagerAdapter;
    private ArrayList<ListView> listViewList;
    private WebtoonListAdapter webtoonListAdapter[] =new WebtoonListAdapter[5];
    private ArrayList<WebtoonData> webtoonDataList[] = new ArrayList[5];

    private TextView tvLoginID;
    private String token;
    private String userId;

    private Context context;
    private SharedPreferences sharedPreferences;

    private String tabNames[] = {"관심웹툰","최근 본 웹툰","임시저장 웹툰","보관함","결제내역"};
    private final int ATTENTION = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_tab, container, false);

        init(v);
        bindViewPagerWithTabLayout();
        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        LoginCheck();
        getWebtoonListByMyAttention();
    }
    private void init(@NotNull View v){
        context = getContext();
        sharedPreferences = context
                .getSharedPreferences(context.getString(R.string.sharedpreference_userdata_filename),Context.MODE_PRIVATE);

        tvLoginID = v.findViewById(R.id.my_tab_login_id);
        tabLayout = v.findViewById(R.id.my_tab_layout);
        viewPager = v.findViewById(R.id.my_tab_viewpager);
        listViewList = new ArrayList<>();
        myTabPagerAdapter = new MyTabPagerAdapter(listViewList, getContext());

        for(int tabIndex = 0; tabIndex< tabNames.length; tabIndex++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabNames[tabIndex]));
            webtoonListAdapter[tabIndex] =
                    new WebtoonListAdapter(getContext(), webtoonDataList[tabIndex], R.layout.item_list_webtoon_loose_form, WebtoonListAdapter.TYPE_LIST);
            listViewList.add(new ListView(getContext()));
            tabListViewSetting(listViewList.get(tabIndex), tabIndex);
        }
        viewPager.setAdapter(myTabPagerAdapter);
    }
    private  void bindViewPagerWithTabLayout(){
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
    }

    private void LoginCheck(){
        token=sharedPreferences.getString("token","");
        userId = sharedPreferences.getString("user_id","");
        if(token.length()==0){
            tvLoginID.setText("로그인하세요.");
            tvLoginID.setTextColor(context.getResources().getColor(R.color.colorBlackfontexplain));
        }else{
            tvLoginID.setText(userId+"님");
            tvLoginID.setTextColor(context.getResources().getColor(R.color.colorBlackfont));
        }
    }

    private void tabListViewSetting(@NotNull ListView listView, final int tabIndex){
        listView.setAdapter(webtoonListAdapter[tabIndex]);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WebtoonData item = (WebtoonData) listView.getItemAtPosition(position);
                if (item.isNone()) return;
                Intent intent = new Intent(getContext(), WebtoonContentsListActivity.class);
                intent.putExtra("comic", item);
                startActivity(intent);
            }
        });
    }
    private void getWebtoonListByMyAttention(){
        Call<ResponseMyWebtoonListData> getAttentionList =
                Singleton.softcomicsService.getMyWebtoonList(token);
        getAttentionList.enqueue(new Callback<ResponseMyWebtoonListData>() {
            @Override
            public void onResponse(Call<ResponseMyWebtoonListData> call, Response<ResponseMyWebtoonListData> response) {
                if(response.isSuccessful()){
                    switch (response.body().getCode()) {
                        case 100://성공적
                            List<WebtoonData> myList = response.body().getWebtoonList();
                            webtoonDataList[ATTENTION] = new ArrayList<>(myList);
                            webtoonListAdapter[ATTENTION].setDataList(webtoonDataList[ATTENTION]);
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

    }
}
