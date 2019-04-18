package com.example.myfirstapp.WebtoonComment.Entities;

import com.example.myfirstapp.common.Entities.RequestContentNoData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestAddCommentData extends RequestContentNoData {

    @SerializedName("comment")
    @Expose
    private String comment;

    public RequestAddCommentData(int contentNo, String comment) {
        super(contentNo);
        this.comment = comment;
    }
}
