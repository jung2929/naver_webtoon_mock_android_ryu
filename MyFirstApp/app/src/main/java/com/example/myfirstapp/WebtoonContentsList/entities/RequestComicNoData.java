package com.example.myfirstapp.WebtoonContentsList.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestComicNoData {
    @SerializedName("comicno")
    @Expose
    private int comicNo;

    public RequestComicNoData(int comicNo) {
        this.comicNo = comicNo;
    }
}
