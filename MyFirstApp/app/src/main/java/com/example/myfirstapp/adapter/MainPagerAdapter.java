package com.example.myfirstapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.myfirstapp.fragment.MainWebtoonTabFragment;
import com.example.myfirstapp.fragment.MyTabFragment;
import com.example.myfirstapp.fragment.SettingTabFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private int numOfTab;
    private Context context;
    public MainPagerAdapter(FragmentManager fm, int numOfTab, Context context) {
        super(fm);
        this.numOfTab=numOfTab;
        this.context=context;
    }

    @Override
    public int getCount() {
        return numOfTab;
    }

    @Override
    public Fragment getItem(int i) {
        Bundle bundle = new Bundle();

        switch (i){
            case 0:
                MainWebtoonTabFragment t0= new MainWebtoonTabFragment();
                return t0;
            case 1:
                SettingTabFragment t1 = new SettingTabFragment();
                return t1;
            case 2:
                SettingTabFragment t2 = new SettingTabFragment();
                return t2;
            case 3:
                MyTabFragment t3 = new MyTabFragment();
                return t3;
            case 4:
                SettingTabFragment t4 = new SettingTabFragment();
                return t4;
            default:
                return null;
        }
    }
}
