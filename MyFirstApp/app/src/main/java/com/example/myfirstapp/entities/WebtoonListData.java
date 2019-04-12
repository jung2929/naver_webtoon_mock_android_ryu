package com.example.myfirstapp.entities;

public class WebtoonListData extends WebtoonData {
    //MainActivity GridView 사용, WebtoonListActivity ListView
    private boolean isNone;

    public WebtoonListData() {
        this.isNone = false;
    }

    public WebtoonListData(String thumbnail, String title, String starPoint, String writer) {
        super(thumbnail, title, starPoint, writer);
        this.isNone = false;
    }

    public WebtoonListData(int comicNO, String comicName, String storyWriter, String paintWriter, String thumbnail, String comicExplain, String comicDay, int comicHeart, String comicRating) {
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
