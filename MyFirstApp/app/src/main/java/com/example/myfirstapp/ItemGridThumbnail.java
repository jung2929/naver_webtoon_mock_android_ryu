package com.example.myfirstapp;

public class ItemGridThumbnail {
    int thumbnail;
    String title;
    String starPoint;
    String writer;
    Boolean isNone;
    Boolean isUpadate;
    Boolean isCuttoon;
    public ItemGridThumbnail(){
        this.isNone=true;
        thumbnail = R.drawable.thumbnail_not_loaded;
        isCuttoon = false;
        isUpadate = false;
    }
    public ItemGridThumbnail(int thumbnail, String title, String starPoint, String writer, Boolean isUpadate, Boolean isCuttoon) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.starPoint = starPoint;
        this.writer = writer;
        this.isUpadate = isUpadate;
        this.isCuttoon = isCuttoon;
        this.isNone = false;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStarPoint() {
        return starPoint;
    }

    public void setStarPoint(String starPoint) {
        this.starPoint = starPoint;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Boolean getUpadate() {
        return isUpadate;
    }

    public void setUpadate(Boolean upadate) {
        isUpadate = upadate;
    }

    public Boolean getCuttoon() {
        return isCuttoon;
    }

    public void setCuttoon(Boolean cuttoon) {
        isCuttoon = cuttoon;
    }
}
