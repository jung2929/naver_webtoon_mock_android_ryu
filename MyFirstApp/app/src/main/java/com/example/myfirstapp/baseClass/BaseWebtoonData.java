package com.example.myfirstapp.baseClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseWebtoonData implements Serializable {
    @SerializedName("Comic_No")
    @Expose
    private int comicNO;
    @SerializedName("Comic_Name")
    @Expose
    private String comicName;
    @SerializedName("Comic_Story")
    @Expose
    private String storyWriter;
    @SerializedName("Comic_Painting")
    @Expose
    private String painter;
    @SerializedName("Comic_Img")
    @Expose
    private String thumbnail;
    @SerializedName("Comic_Text")
    @Expose
    private String comicExplain;
    @SerializedName("Comic_Day")
    @Expose
    private String comicDay;
    @SerializedName("Comic_Heart")
    @Expose
    private int comicHeart;
    @SerializedName("Comic_Rating")
    @Expose
    private String comicRating;


    public BaseWebtoonData() {
        thumbnail = null;
    }

    public BaseWebtoonData(String thumbnail, String comicName, String comicRating, String storyWriter) {
        this.thumbnail = thumbnail;
        this.comicName = comicName;
        this.comicRating = comicRating;
        this.storyWriter = storyWriter;
    }

    public BaseWebtoonData(int comicNO, String comicName, String storyWriter, String painter, String thumbnail, String comicExplain, String comicDay, int comicHeart, String comicRating) {
        this.comicNO = comicNO;
        this.comicName = comicName;
        this.storyWriter = storyWriter;
        this.painter = painter;
        this.thumbnail = thumbnail;
        this.comicExplain = comicExplain;
        this.comicDay = comicDay;
        this.comicHeart = comicHeart;
        this.comicRating = comicRating;
    }

    public int getComicNO() {
        return comicNO;
    }

    public void setComicNO(int comicNO) {
        this.comicNO = comicNO;
    }

    public void setComicName(String comicName) {
        this.comicName = comicName;
    }

    public void setStoryWriter(String storyWriter) {
        this.storyWriter = storyWriter;
    }

    public String getPainter() {
        return painter;
    }

    public void setPainter(String painter) {
        this.painter = painter;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getComicExplain() {
        return comicExplain;
    }

    public void setComicExplain(String comicExplain) {
        this.comicExplain = comicExplain;
    }

    public String getComicDay() {
        return comicDay;
    }

    public void setComicDay(String comicDay) {
        this.comicDay = comicDay;
    }

    public int getComicHeart() {
        return comicHeart;
    }

    public void setComicHeart(int comicHeart) {
        this.comicHeart = comicHeart;
    }

    public void setComicRating(String comicRating) {
        this.comicRating = comicRating;
    }

    public String getStoryWriter() {
        return storyWriter;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getComicName() {
        return comicName;
    }

    public String getComicRating() {
        return comicRating;
    }

}
