package com.example.myfirstapp.WebtoonComment.Entities;

import com.example.myfirstapp.baseClass.ResponseBaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCommentBehaviorData extends ResponseBaseData {

    @SerializedName("like")
    @Expose
    private int like;
    @SerializedName("dislike")
    @Expose
    private int dislike;

    public int getLike() {
        return like;
    }

    public int getDislike() {
        return dislike;
    }

    public ResponseCommentBehaviorData(Integer code, String message) {
        super(code, message);
    }
}
