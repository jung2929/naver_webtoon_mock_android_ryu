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

import com.example.myfirstapp.R;
import com.example.myfirstapp.activities.WebtoonListActivity;
import com.example.myfirstapp.activities.WebtoonSearchActivity;
import com.example.myfirstapp.adapter.WebtoonDaysPageAdapter;
import com.example.myfirstapp.adapter.WebtoonListAdapter;
import com.example.myfirstapp.entities.WebtoonListData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.example.myfirstapp.GlobalApplication.webtoonList;
import static com.facebook.FacebookSdk.getApplicationContext;

public class MainWebtoonTabFragment extends Fragment {
    private View searchButton;

    private String days[] = {"월","화","수","목","금","토","일","신작","완결"};
    GridView gridView[] = new GridView[days.length];
    WebtoonListAdapter gridThumbnailAdapter;

    ViewPager viewPager;
    TabLayout tabLayout;
    WebtoonDaysPageAdapter webtoonDaysPageAdapter;
    ArrayList<GridView> gridViewArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_webtoonlist_tab, container, false);
        searchButton = v.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WebtoonSearchActivity.class);
                startActivity(intent);
            }
        });

        gridThumbnailAdapter = new WebtoonListAdapter(getApplicationContext(), R.layout.item_list_webtoon_square_form,WebtoonListAdapter.TYPE_GRID);

        for(WebtoonListData e : webtoonList){
            gridThumbnailAdapter.addItem(e);
        }

        for (int i = 0; i < days.length; i++) {
            gridView[i] = new GridView(getApplicationContext());
            gridView[i].setNumColumns(3);
            gridView[i].setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gridView[i].setBackgroundResource(R.color.border);

            gridView[i].setAdapter(gridThumbnailAdapter);
            int finalI = i;
            gridView[i].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    WebtoonListData item = (WebtoonListData) gridView[finalI].getItemAtPosition(position);
                    if (item.isNone()) return;
                    String webtoonName = item.getTitle();
                    Intent intent = new Intent(getApplicationContext(), WebtoonListActivity.class);
                    intent.putExtra("webtoonName", webtoonName);
                    startActivity(intent);
                }
            });
        }

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
