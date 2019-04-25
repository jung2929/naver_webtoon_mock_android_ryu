package com.example.myfirstapp.Main;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp.Main.Entities.MenuTabItem;
import com.example.myfirstapp.NonSwipeableViewPager;
import com.example.myfirstapp.Main.Adpater.MainPagerAdapter;
import com.example.myfirstapp.R;
import com.example.myfirstapp.Singleton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import static android.support.constraint.Constraints.TAG;

public class MainActivity extends AppCompatActivity {

    private MenuTabItem menuTabItem[]=new MenuTabItem[5];
    private TabLayout menuTabLayout;
    private NonSwipeableViewPager mainViewPager;
    private MainPagerAdapter mainPagerAdapter;
    final int MAIN_TAB_COUNT=5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setMenuTabLayout();
        setMainViewPager();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Singleton.isStartActivity = false;
    }
    private void init(){
        menuTabLayout= findViewById(R.id.main_menu_tab);
        mainViewPager = findViewById(R.id.main_viewpager);
        mainPagerAdapter = new MainPagerAdapter
                (getSupportFragmentManager(), MAIN_TAB_COUNT, this);

    }
    private void setMenuTabLayout(){
        menuTabItem[0]=new MenuTabItem(R.drawable.ic_clicked_webtoon,R.drawable.ic_clicked_webtoon,"웹툰");
        menuTabItem[1]=new MenuTabItem(R.drawable.ic_unclicked_best_challenge,R.drawable.ic_clicked_webtoon,"베스트도전");
        menuTabItem[2]=new MenuTabItem(R.drawable.ic_unclicked_play,R.drawable.ic_clicked_webtoon,"PLAY");
        menuTabItem[3]=new MenuTabItem(R.drawable.ic_unclicked_mymenu,R.drawable.ic_clicked_webtoon,"MY");
        menuTabItem[4]=new MenuTabItem(R.drawable.ic_unclicked_setting,R.drawable.ic_clicked_webtoon,"설정");

        menuTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        for(int i=0; i<MAIN_TAB_COUNT; i++) {
            View tab = createMenuTabView(menuTabItem[i], false);
            menuTabLayout.addTab(menuTabLayout.newTab().setCustomView(tab));
        }

        menuTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int i=tab.getPosition();
                mainViewPager.setCurrentItem(i);
                View newTab = tab.getCustomView();
                changeMenuTabView(newTab, i,true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int i=tab.getPosition();;
                View newTab = tab.getCustomView();
                changeMenuTabView(newTab, i,false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void setMainViewPager(){
        mainViewPager.setAdapter(mainPagerAdapter);
    }

    private View createMenuTabView(MenuTabItem menuTabItem, boolean clicked){
        View tab = LayoutInflater.from(this).inflate(R.layout.item_menubar_tab, menuTabLayout, false);
        TextView tabText = tab.findViewById(R.id.tab_text);
        ImageView tabImage=tab.findViewById(R.id.tab_image);
        tabText.setText(menuTabItem.getText());
        if(clicked) {
            tabText.setTextColor(getResources().getColor(R.color.colorClickedMenuBar));
            tabImage.setImageResource(menuTabItem.getClickedImage());
        }
        else{
            tabText.setTextColor(getResources().getColor(R.color.colorUnclickedMenuBar));
            tabImage.setImageResource(menuTabItem.getUnClickedImage());
        }
        return tab;
    }
    private void changeMenuTabView(View tab, int tabIndex,boolean clicked){
        TextView tabText = tab.findViewById(R.id.tab_text);
        ImageView tabImage=tab.findViewById(R.id.tab_image);
        if(clicked) {
            tabText.setTextColor(getResources().getColor(R.color.colorClickedMenuBar));
            tabImage.setImageResource(menuTabItem[tabIndex].getClickedImage());
        }
        else{
            tabText.setTextColor(getResources().getColor(R.color.colorUnclickedMenuBar));
            tabImage.setImageResource(menuTabItem[tabIndex].getUnClickedImage());
        }
    }
}
