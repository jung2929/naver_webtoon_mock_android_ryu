package com.example.myfirstapp;

import com.example.myfirstapp.common.entities.WebtoonData;

import java.util.ArrayList;

public class DataManager{
    static void initWebtoonDummyData(ArrayList<WebtoonData> webtoonDataList){
        webtoonDataList.add(new WebtoonData(null, "소녀의 세계", "5.0","모랑지"));
        webtoonDataList.add(new WebtoonData(null, "복학왕", "5.0","기안84"));
        webtoonDataList.add(new WebtoonData(0,"데드라이프","김작가","이작가",null,"좀비물","월",5,"9.99"));
        webtoonDataList.add(new WebtoonData(null, "소녀의 세계", "5.0","모랑지"));
    }
}
