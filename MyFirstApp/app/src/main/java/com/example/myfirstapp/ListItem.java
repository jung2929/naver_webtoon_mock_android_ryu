package com.example.myfirstapp;

import java.util.List;

public class ListItem {

    int Itemtype;

    int image;
    int ivThumbnail;//R.drawable.id
    String tvTitle;
    String tvStarPoint;
    String tvDate;
    boolean read;
    public ListItem(){

    }
    public ListItem(String tvTitle, int Thumbnail){
        this.tvTitle = tvTitle;
        this.ivThumbnail = Thumbnail;
    }
    public ListItem(int ivThumbnail, String tvTitle, String tvStarPoint, String tvDate, boolean read) {
        this.ivThumbnail = ivThumbnail;
        this.tvTitle = tvTitle;
        this.tvStarPoint = tvStarPoint;
        this.tvDate = tvDate;
        this.read = read;
    }
    public int getIvThumbnail() {
        return ivThumbnail;
    }

    public void setIvThumbnail(int ivThumbnail) {
        this.ivThumbnail = ivThumbnail;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public String getTvStarPoint() {
        return tvStarPoint;
    }

    public void setTvStarPoint(String tvStarPoint) {
        this.tvStarPoint = tvStarPoint;
    }

    public String getTvDate() {
        return tvDate;
    }

    public void setTvDate(String tvDate) {
        this.tvDate = tvDate;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public int getItemtype() {
        return Itemtype;
    }

    public void setItemtype(int itemtype) {
        Itemtype = itemtype;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image=image;
    }

}
