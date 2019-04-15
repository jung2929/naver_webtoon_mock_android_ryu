package com.example.myfirstapp.WebtoonContentsListActivity.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestAddAttentionData {
    @SerializedName("comicno")
    @Expose
    private int comicNo;

    public RequestAddAttentionData(int comicNo) {
        this.comicNo = comicNo;
    }
}
