package com.example.myfirstapp.WebtoonContentsList.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WebtoonContentsData implements Serializable {
    //WebtoonContentsListActivity ListView 사용

    @SerializedName("Content_No")
    @Expose
    private int contentNo;
    @SerializedName("Comic_No")
    @Expose
    private int comicNo;
    @SerializedName("Content_Img")
    @Expose
    private String contentImg;
    @SerializedName("Content_Name")
    @Expose
    private String contentName;
    @SerializedName("Content_Content")
    @Expose
    private String contentContent;
    @SerializedName("Content_Date")
    @Expose
    private String contentDate;
    @SerializedName("Content_Heart")
    @Expose
    private int contentHeart;
    @SerializedName("Content_Type")
    @Expose
    private int contentType;
    @SerializedName("Content_Rating")
    @Expose
    private String contentRating;

    private boolean isRead;

    public WebtoonContentsData(String contentName, String contentImg) {
        this.contentName = contentName;
        this.contentImg = contentImg;
    }

    public WebtoonContentsData(String contentImg, String contentName, String contentRating, String contentDate, boolean isRead) {
        this.contentImg = contentImg;
        this.contentName = contentName;
        this.contentRating = contentRating;
        this.contentDate = contentDate;
        this.isRead = isRead;
    }

    public String getContentImg() {
        return contentImg;
    }

    public String getContentName() {
        return contentName;
    }

    public String getContentDate() {
        return contentDate;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        this.isRead = read;
    }

    public int getContentNo() {
        return contentNo;
    }

    public void setContentNo(int contentNo) {
        this.contentNo = contentNo;
    }

    public int getComicNo() {
        return comicNo;
    }

    public void setComicNo(int comicNo) {
        this.comicNo = comicNo;
    }

    public void setContentImg(String contentImg) {
        this.contentImg = contentImg;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentContent() {
        return contentContent;
    }

    public void setContentContent(String contentContent) {
        this.contentContent = contentContent;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public void setContentDate(String contentDate) {
        this.contentDate = contentDate;
    }

    public int getContentHeart() {
        return contentHeart;
    }

    public void setContentHeart(int contentHeart) {
        this.contentHeart = contentHeart;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

}
