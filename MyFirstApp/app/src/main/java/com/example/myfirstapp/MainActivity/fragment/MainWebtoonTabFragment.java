package com.example.myfirstapp.MainActivity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.myfirstapp.GlobalApplication;
import com.example.myfirstapp.R;
import com.example.myfirstapp.WebtoonContentsListActivity.WebtoonContentsListActivity;
import com.example.myfirstapp.WebtoonSearchActivity.WebtoonSearchActivity;
import com.example.myfirstapp.MainActivity.adpater.WebtoonDaysPageAdapter;
import com.example.myfirstapp.common.adapter.WebtoonListAdapter;
import com.example.myfirstapp.MainActivity.entities.ResponseWebtoonListData;
import com.example.myfirstapp.common.entities.WebtoonData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainWebtoonTabFragment extends Fragment {

    private Context context;
    private View searchButton;

    private String DAYS[] = {"월", "화", "수", "목", "금", "토", "일", "신작", "완결"};
    private final String mDaysEng[] = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
    private final int MONDAY = 0;
    private final int TUESDAY = 1;
    private final int WEDNESDAY = 2;
    private final int THURSDAY = 3;
    private final int FRIDAY = 4;
    private final int SATURDAY = 5;
    private final int SUNDAY = 6;

    private ArrayList<WebtoonData> webtoonDataList[] = new ArrayList[DAYS.length];
    private WebtoonListAdapter webtoonListAdapter[] = new WebtoonListAdapter[DAYS.length];

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private WebtoonDaysPageAdapter webtoonDaysPageAdapter;
    private ArrayList<GridView> gridViewList;

    private int mDay;

    void init(View v){
        context = getContext();
        searchButton = v.findViewById(R.id.searchButton);
        viewPager = v.findViewById(R.id.viewpager_webtoonlist);
        tabLayout = v.findViewById(R.id.tlWebtoonDays);
        gridViewList = new ArrayList<>();
        webtoonDaysPageAdapter = new WebtoonDaysPageAdapter(gridViewList, context);

        for (mDay = MONDAY; mDay < DAYS.length; mDay++) {
            webtoonListAdapter[mDay] = new WebtoonListAdapter(getContext(), webtoonDataList[mDay],R.layout.item_list_webtoon_square_form, WebtoonListAdapter.TYPE_GRID);
            gridViewList.add(new GridView(context));
            TabLayout.Tab t = tabLayout.newTab();
            t.setText(DAYS[mDay]);
            tabLayout.addTab(t);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_webtoonlist_tab, container, false);

        init(v);
        toSearchActivitySetting();
        for (int day = MONDAY; day <= SUNDAY; day++) {
            getWebtoonListBy(day);
        }
        setWebtoonGridView();
        bindViewPagerAndTabLayout();
        selectTabByCurrentTime();
        return v;
    }

    void setWebtoonGridView(){
    for (mDay = MONDAY; mDay < DAYS.length; mDay++) {
        gridViewList.get(mDay).setNumColumns(3);
        gridViewList.get(mDay).setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridViewList.get(mDay).setBackgroundResource(R.color.border);
        gridViewList.get(mDay).setAdapter(webtoonListAdapter[mDay]);

        final int finalDay = mDay;
        gridViewList.get(mDay).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WebtoonData item = (WebtoonData)  gridViewList.get(finalDay).getItemAtPosition(position);
                if (item.isNone()) return;
                Intent intent = new Intent(context, WebtoonContentsListActivity.class);
                intent.putExtra("comic", item);
                startActivity(intent);
            }
        });
    }
    viewPager.setAdapter(webtoonDaysPageAdapter);
}
    void getWebtoonListBy(final int day) {
        Call<ResponseWebtoonListData> webtoonListDataCall = GlobalApplication.softcomicsService.getDaysWebtoonList(mDaysEng[day]);
        webtoonListDataCall.enqueue(new Callback<ResponseWebtoonListData>() {
            @Override
            public void onResponse(Call<ResponseWebtoonListData> call, Response<ResponseWebtoonListData> response) {
                if (response.isSuccessful()) {
                    switch (response.body().getCode()) {
                        case 100://가져오기 성공
                            List<WebtoonData> list = response.body().getResult();
                            webtoonDataList[day] = (ArrayList)list;
                            Log.d("웹툰 출력", webtoonDataList[day].size() + " : " + webtoonDataList[day]);
                            webtoonListAdapter[day].notifyDataSetChanged();
                            break;
                        default:
                            Toast.makeText(getContext(), "code : " + response.body().getCode(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("에러내용 ", call.toString());
                    Toast.makeText(getContext(), "문제생김", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseWebtoonListData> call, Throwable t) {
                Toast.makeText(getContext(), "서버로부터 웹툰 정보를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    void toSearchActivitySetting(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebtoonSearchActivity.class);
                startActivity(intent);
            }
        });

    }
    void bindViewPagerAndTabLayout(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //이게 네이버웹툰 방식인듯
                tabLayout.setScrollPosition(i, 0f, true);
                //tabLayout.getTabAt(i).select();
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
    void selectTabByCurrentTime(){
        Calendar c = new GregorianCalendar(Locale.KOREA);
        int day = (c.get(c.DAY_OF_WEEK) + 5) % 7;
        tabLayout.getTabAt(day).select();
    }
}


