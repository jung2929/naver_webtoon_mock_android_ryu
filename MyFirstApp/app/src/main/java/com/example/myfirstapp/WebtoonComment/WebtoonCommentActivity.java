package com.example.myfirstapp.WebtoonComment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.myfirstapp.R;

public class WebtoonCommentActivity extends AppCompatActivity {

    TabLayout tlComments;
    ViewPager vpComments;
    private void init(){
        tlComments = findViewById(R.id.webtoon_comment_tab_layout);
        vpComments = findViewById(R.id.webtoon_comment_view_pager);

        setTabLayout();
        setViewPager();
        bindTabLayoutAndViewPager();
    }
    private void setTabLayout(){
        tlComments.addTab(tlComments.newTab().setText("베스트댓글"));
        tlComments.addTab(tlComments.newTab().setText("전체댓글"));
    }
    private void setViewPager(){

    }
    private void bindTabLayoutAndViewPager(){
        tlComments.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                vpComments.setCurrentItem(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        vpComments.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tlComments.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_comment);

        init();
        setTabLayout();
    }
}
