package com.example.myfirstapp.WebtoonComment.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.myfirstapp.WebtoonComment.Fragment.WebtoonCommentFragment;

public class CommentTypePagerAdapter extends FragmentStatePagerAdapter {
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
    public int getItemPosition(Object object) {
            return POSITION_NONE;
    }
    @Override
    public int getCount() {
        return numOfTab;
    }

    @Override
    public Fragment getItem(int i) {
        Bundle bundle = new Bundle(1);
        bundle.putInt("content_no", contentNo);

        // 0 : BEST COMMENT
        // 1 : ALL COMMENT
        bundle.putInt("comment_type", i);
        switch (i) {
            case 0:
                WebtoonCommentFragment best = new WebtoonCommentFragment();
                best.setArguments(bundle);
                return best;
            case 1:
                WebtoonCommentFragment all = new WebtoonCommentFragment();
                all.setArguments(bundle);
                return all;
            default:
                return null;
        }
    }
}
