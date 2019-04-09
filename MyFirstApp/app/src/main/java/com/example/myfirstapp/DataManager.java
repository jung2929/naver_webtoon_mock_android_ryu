package com.example.myfirstapp;

import com.example.myfirstapp.entities.WebtoonListData;

import java.util.ArrayList;

public class DataManager {

    static void initWebtoonDummyData(ArrayList<WebtoonListData> webtoonDataList){
//        public static String webtoonNames[] = {"소녀의 세계", "복학왕", "데드라이프", "abcd", "abcde", "aaa"};
//        public static int webtoonThumnails[] = {R.drawable.thumbnail_world_of_girl, R.drawable.thumbnail_king, R.drawable.thumbnail_dead_life
//                ,R.drawable.thumbnail_not_loaded, R.drawable.thumbnail_not_loaded,R.drawable.thumbnail_not_loaded};
        //(int thumbnail, String title, String starPoint, String writer, Boolean isUpadate, Boolean isCuttoon) {

        webtoonDataList.add(new WebtoonListData(R.drawable.thumbnail_world_of_girl, "소녀의 세계", "5.0","모랑지"));
        webtoonDataList.add(new WebtoonListData(R.drawable.thumbnail_king, "복학왕", "5.0","기안84"));
        webtoonDataList.add(new WebtoonListData(R.drawable.thumbnail_world_of_girl, "소녀의 세계", "5.0","모랑지"));
        webtoonDataList.add(new WebtoonListData(R.drawable.thumbnail_world_of_girl, "소녀의 세계", "5.0","모랑지"));
    }
}
