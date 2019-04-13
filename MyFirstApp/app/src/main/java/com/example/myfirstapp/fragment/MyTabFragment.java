package com.example.myfirstapp.fragment;

import android.content.Intent;
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

import com.example.myfirstapp.GlobalApplication;
import com.example.myfirstapp.R;
import com.example.myfirstapp.activities.WebtoonContentsListActivity;
import com.example.myfirstapp.adapter.MyTabPagerAdapter;
import com.example.myfirstapp.adapter.WebtoonListAdapter;
import com.example.myfirstapp.entities.WebtoonListData;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MyTabFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyTabPagerAdapter myTabPagerAdapter;
    private ArrayList<ListView> list;
    private WebtoonListAdapter webtoonListAdapter[] =new WebtoonListAdapter[5];
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_tab, container, false);
        tabLayout = v.findViewById(R.id.my_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("관심웹툰"));
        tabLayout.addTab(tabLayout.newTab().setText("최근 본 웹툰"));
        tabLayout.addTab(tabLayout.newTab().setText("임시저장 웹툰"));
        tabLayout.addTab(tabLayout.newTab().setText("보관함"));
        tabLayout.addTab(tabLayout.newTab().setText("결제내역"));

        //관심웹툰 리스트 세팅
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
}
