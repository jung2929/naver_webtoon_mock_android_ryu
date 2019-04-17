package com.example.myfirstapp.WebtoonSearch.entities;

import com.example.myfirstapp.baseClass.BaseWebtoonData;

public class WebtoonSearchData extends BaseWebtoonData {
    public WebtoonSearchData(String thumbnail, String title, String starPoint, String writer) {
        super(thumbnail, title, starPoint, writer);
    }

    public WebtoonSearchData(int comicNO, String comicName, String storyWriter, String paintWriter, String thumbnail, String comicExplain, String comicDay, int comicHeart, String comicRating) {
        super(comicNO, comicName, storyWriter, paintWriter, thumbnail, comicExplain, comicDay, comicHeart, comicRating);
    }
}
