package com.example.myfirstapp.WebtoonViewer.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WebtoonContentViewData {
    @SerializedName("Content_Content")
    @Expose
    private String contentContent;

    public String getContentContent() {
        return contentContent;
    }

    public void setContentContent(String contentContent) {
        this.contentContent = contentContent;
    }
}
