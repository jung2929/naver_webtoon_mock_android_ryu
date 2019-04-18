package com.example.myfirstapp.WebtoonViewer.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestPutRatingData {
    @SerializedName("contentno")
    @Expose
    private int contentNo;
    @SerializedName("contentrate")
    @Expose
    private float contentRate;
    public RequestPutRatingData(int contentNo, float contentRate) {
        this.contentNo = contentNo;
        this.contentRate = contentRate;
    }
}
