package com.example.myfirstapp.common.Entities;

import com.example.myfirstapp.baseClass.BaseWebtoonData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * MainActivity GridView 사용, WebtoonListActivity ListView
 */
public class WebtoonData extends BaseWebtoonData {
    private boolean isNone;

    private int check;

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

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }
}
