package com.example.myfirstapp.common.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestContentNoData {

    @SerializedName("contentno")
    @Expose
    private int contentNo;

    public RequestContentNoData(int contentNo) {
        this.contentNo = contentNo;
    }
}
