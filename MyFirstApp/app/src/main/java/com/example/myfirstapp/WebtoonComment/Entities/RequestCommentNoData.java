package com.example.myfirstapp.WebtoonComment.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestCommentNoData {
    @SerializedName("commentno")
    @Expose
    private int commentNo;

    public RequestCommentNoData(int commentNo) {
        this.commentNo = commentNo;
    }
}
