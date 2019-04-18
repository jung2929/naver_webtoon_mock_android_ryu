package com.example.myfirstapp.WebtoonComment.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myfirstapp.R;

public class WebtoonBestCommentFragment extends Fragment {

    private int contentNo;
    private void init(final View v){

        getArgumentsAndSet();
    }
    private void getArgumentsAndSet(){
        contentNo = getArguments().getInt("content_no");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_webtoon_best_comment, container, false);

        init(v);
        return v;
    }


}
