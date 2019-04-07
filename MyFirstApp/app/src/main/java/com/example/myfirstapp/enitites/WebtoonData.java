package com.example.myfirstapp.enitites;

import com.example.myfirstapp.R;

public class WebtoonData {
    private int thumbnail;
    private String title;
    private String starPoint;
    private String writer;
    public WebtoonData(){
        thumbnail = R.drawable.thumbnail_not_loaded;
    }

    public WebtoonData(int thumbnail, String title, String starPoint, String writer) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.starPoint = starPoint;
        this.writer=writer;
    }

    public String getWriter() {
        return writer;
    }

    public int getThumbnail() {
        return thumbnail;
    }


    public String getTitle() {
        return title;
    }

    public String getStarPoint() {
        return starPoint;
    }

}
