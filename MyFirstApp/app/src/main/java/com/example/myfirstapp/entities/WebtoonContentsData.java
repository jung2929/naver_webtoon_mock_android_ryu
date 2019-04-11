package com.example.myfirstapp.entities;

public class WebtoonContentsData {
    //WebtoonContentsListActivity ListView 사용
    private String thumbnail;
    private String title;
    private String comicRating;
    private String date;
    private boolean isRead;

    public WebtoonContentsData(){

    }
    public WebtoonContentsData(String title, String Thumbnail){
        this.title = title;
        this.thumbnail = Thumbnail;
    }
    public WebtoonContentsData(String thumbnail, String title, String comicRating, String date, boolean isRead) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.comicRating = comicRating;
        this.date = date;
        this.isRead = isRead;
    }

    public String getThumbnail() {
        return thumbnail;
    }
    public String getTitle() {
        return title;
    }

    public String getComicRating() {
        return comicRating;
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
}
