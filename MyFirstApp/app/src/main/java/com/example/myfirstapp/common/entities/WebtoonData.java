package com.example.myfirstapp.common.Entities;

import com.example.myfirstapp.baseClass.BaseWebtoonData;

/**
 * MainActivity GridView 사용, WebtoonListActivity ListView
 */
public class WebtoonData extends BaseWebtoonData {
    private boolean isNone;

    public WebtoonData() {
        this.isNone = false;
    }

    public WebtoonData(String thumbnail, String title, String starPoint, String writer) {
        super(thumbnail, title, starPoint, writer);
        this.isNone = false;
    }

    public WebtoonData(int comicNO, String comicName, String storyWriter, String paintWriter, String thumbnail, String comicExplain, String comicDay, int comicHeart, String comicRating) {
        super(comicNO, comicName, storyWriter, paintWriter, thumbnail, comicExplain, comicDay, comicHeart, comicRating);
        this.isNone = false;
    }

    public boolean isNone() {
        return isNone;
    }

    public void setNone(boolean none) {
        isNone = none;
    }
}
