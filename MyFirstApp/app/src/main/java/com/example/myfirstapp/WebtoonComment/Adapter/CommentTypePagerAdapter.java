package com.example.myfirstapp.WebtoonComment.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.myfirstapp.Main.Fragment.MainWebtoonTabFragment;
import com.example.myfirstapp.Main.Fragment.MyTabFragment;
import com.example.myfirstapp.Main.Fragment.SettingTabFragment;
import com.example.myfirstapp.WebtoonComment.Fragment.WebtoonBestCommentFragment;

public class CommentTypePagerAdapter extends FragmentPagerAdapter {
    private int numOfTab;
    private Context context;
    private int contentNo;

    public CommentTypePagerAdapter(FragmentManager fm, int numOfTab, Context context, int contentNo) {
        super(fm);
        this.numOfTab = numOfTab;
        this.context = context;
        this.contentNo = contentNo;
    }

    @Override
    public int getCount() {
        return numOfTab;
    }

    @Override
    public Fragment getItem(int i) {
        Bundle bundle = new Bundle(1);
        bundle.putInt("content_no", contentNo);
        switch (i) {
            case 0:
                WebtoonBestCommentFragment best = new WebtoonBestCommentFragment();
                best.setArguments(bundle);
                return best;
            case 1:
                WebtoonBestCommentFragment best2 = new WebtoonBestCommentFragment();
                best2.setArguments(bundle);
                return best2;
            default:
                return null;
        }
    }
}
