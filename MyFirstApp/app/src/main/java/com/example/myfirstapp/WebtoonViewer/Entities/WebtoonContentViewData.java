package com.example.myfirstapp.WebtoonViewer.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WebtoonContentViewData {
    @SerializedName("Content_Content")
    @Expose
    private String contentContent;
    @SerializedName("check")
    @Expose
    private int check;

    public String getContentContent() {
        return contentContent;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }
}
