package com.example.myfirstapp.entities;

public class WebtoonContentsData {
    //WebtoonListActivity ListView 사용
    private int itemType;
    private String thumbnail;
    private String title;
    private String starPoint;
    private String date;
    private boolean isRead;

    public WebtoonContentsData(){

    }
    public WebtoonContentsData(String title, String Thumbnail){
        this.title = title;
        this.thumbnail = Thumbnail;
    }
    public WebtoonContentsData(String thumbnail, String title, String starPoint, String date, boolean isRead) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.starPoint = starPoint;
        this.date = date;
        this.isRead = isRead;
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

    public String getDate() {
        return date;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        this.isRead = read;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }




}
