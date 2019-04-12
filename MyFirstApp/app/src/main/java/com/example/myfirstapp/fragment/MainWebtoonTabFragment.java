package com.example.myfirstapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.myfirstapp.GlobalApplication;
import com.example.myfirstapp.R;
import com.example.myfirstapp.activities.WebtoonContentsListActivity;
import com.example.myfirstapp.activities.WebtoonSearchActivity;
import com.example.myfirstapp.adapter.WebtoonDaysPageAdapter;
import com.example.myfirstapp.adapter.WebtoonListAdapter;
import com.example.myfirstapp.entities.ResponseWebtoonListData;
import com.example.myfirstapp.entities.WebtoonListData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myfirstapp.GlobalApplication.webtoonList;
import static com.facebook.FacebookSdk.getApplicationContext;

public class MainWebtoonTabFragment extends Fragment {
    private View searchButton;

    private String days[] = {"월","화","수","목","금","토","일","신작","완결"};
    private final String daysEng[]={"monday","tuesday","wednesday","thursday","friday","saturday","sunday"};
    private final int MONDAY=0;
    private final int TUESDAY=1;
    private final int WEDNESDAY=2;
    private final int THURSDAY=3;
    private final int FRIDAY=4;
    private final int SATURDAY=5;
    private final int SUNDAY=6;
    GridView gridView[] = new GridView[days.length];
    WebtoonListAdapter webtoonListAdapter[] = new WebtoonListAdapter[days.length];

    ViewPager viewPager;
    TabLayout tabLayout;
    WebtoonDaysPageAdapter webtoonDaysPageAdapter;
    ArrayList<GridView> gridViewArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_webtoonlist_tab, container, false);
        //검색 버튼
        searchButton = v.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WebtoonSearchActivity.class);
                startActivity(intent);
            }
        });

        //웹툰 리스트 GirdView
        for(int i=0; i<days.length; i++)
            webtoonListAdapter[i] = new WebtoonListAdapter(getApplicationContext(), R.layout.item_list_webtoon_square_form,WebtoonListAdapter.TYPE_GRID);
        for(int i=0; i<7;i++) {
            final int finalI=i;
            Call<ResponseWebtoonListData> webtoonListDataCall = GlobalApplication.softcomicsservice.getDaysWebtoonList(daysEng[i]);
            webtoonListDataCall.enqueue(new Callback<ResponseWebtoonListData>() {
                @Override
                public void onResponse(Call<ResponseWebtoonListData> call, Response<ResponseWebtoonListData> response) {
                    if (response.isSuccessful()) {
                        switch (response.body().getCode()) {
                            case 100://가져오기 성공
                                List<WebtoonListData> list = response.body().getResult();
                                for (int j = 0; j < list.size(); j++) {
                                    webtoonListAdapter[finalI].addItem(list.get(j));
                                }
                                webtoonListAdapter[finalI].notifyDataSetChanged();
                                break;
                            default:
                                Toast.makeText(getContext(), "code : " + response.body().getCode(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "문제생김", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseWebtoonListData> call, Throwable t) {
                    Toast.makeText(getContext(), "서버로부터 웹툰 정보를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
                }
            });

        }
        //요일 page별로 adapter 묶기
        for (int i = 0; i < days.length; i++) {
            gridView[i] = new GridView(getApplicationContext());
            gridView[i].setNumColumns(3);
            gridView[i].setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gridView[i].setBackgroundResource(R.color.border);

            gridView[i].setAdapter(webtoonListAdapter[i]);
            int finalI = i;
            gridView[i].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    WebtoonListData item = (WebtoonListData) gridView[finalI].getItemAtPosition(position);
                    if (item.isNone()) return;
                    Intent intent = new Intent(getApplicationContext(), WebtoonContentsListActivity.class);
                    intent.putExtra("comic", item);
                    startActivity(intent);
                }
            });
        }

        //요일 GridView 바꾸는 ViewPager 세팅
        gridViewArrayList = new ArrayList<>();
        for (int i = 0; i < days.length; i++) {
            gridViewArrayList.add(gridView[i]);
        }
        webtoonDaysPageAdapter = new WebtoonDaysPageAdapter(gridViewArrayList, getApplicationContext());
        viewPager = v.findViewById(R.id.viewpager_webtoonlist);
        viewPager.setAdapter(webtoonDaysPageAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //이게 네이버웹툰 방식인듯
                tabLayout.setScrollPosition(i,0f,true);
                //tabLayout.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //days 탭 생성
        tabLayout = v.findViewById(R.id.tlWebtoonDays);
        for (int i = 0; i < days.length; i++) {
            TabLayout.Tab t=tabLayout.newTab();
            t.setText(days[i]);
            tabLayout.addTab(t);
        }
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

        //현재 요일에 따른 탭 자동선택
        Calendar c=new GregorianCalendar(Locale.KOREA);
        int day = (c.get(c.DAY_OF_WEEK)+5)%7;
        tabLayout.getTabAt(day).select();
        return v;
    }

}
