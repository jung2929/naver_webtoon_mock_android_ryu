package com.example.myfirstapp.WebtoonViewer.Entities;

import com.example.myfirstapp.baseClass.ResponseBaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAddLikeContentData extends ResponseBaseData {
    @SerializedName("like")
    @Expose
    private int like;

    public ResponseAddLikeContentData(Integer code, String message, int like) {
        super(code, message);
        this.like = like;
    }

    public int getLike() {
        return like;
    }
}
