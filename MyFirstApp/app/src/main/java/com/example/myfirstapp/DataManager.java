package com.example.myfirstapp;

import android.app.Application;

import com.example.myfirstapp.entities.WebtoonListData;

import java.util.ArrayList;

public class DataManager{
    static void initWebtoonDummyData(ArrayList<WebtoonListData> webtoonDataList){
        webtoonDataList.add(new WebtoonListData(null, "소녀의 세계", "5.0","모랑지"));
        webtoonDataList.add(new WebtoonListData(null, "복학왕", "5.0","기안84"));
        webtoonDataList.add(new WebtoonListData(0,"데드라이프","김작가","이작가",null,"좀비물","월",5,"9.99"));
        webtoonDataList.add(new WebtoonListData(null, "소녀의 세계", "5.0","모랑지"));
    }
}
