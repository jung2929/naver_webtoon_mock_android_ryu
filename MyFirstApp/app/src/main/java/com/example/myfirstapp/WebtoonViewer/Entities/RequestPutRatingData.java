package com.example.myfirstapp.WebtoonViewer.Entities;

import com.example.myfirstapp.common.Entities.RequestContentNoData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestPutRatingData extends RequestContentNoData {
    @SerializedName("contentrate")
    @Expose
    private int contentRate;

    public RequestPutRatingData(int contentNo, int contentRate) {
        super(contentNo);
        this.contentRate = contentRate;
    }
}
