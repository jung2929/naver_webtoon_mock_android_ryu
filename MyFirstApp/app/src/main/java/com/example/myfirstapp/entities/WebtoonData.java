package com.example.myfirstapp.entities;

import com.example.myfirstapp.R;

public class WebtoonData {
    private String thumbnail;
    private String title;
    private String starPoint;
    private String writer;
    public WebtoonData(){
        thumbnail=null;
    }

    public WebtoonData(String thumbnail, String title, String starPoint, String writer) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.starPoint = starPoint;
        this.writer=writer;
    }

    public String getWriter() {
        return writer;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public String getStarPoint() {
        return starPoint;
    }

}
