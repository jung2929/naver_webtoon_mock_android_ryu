package com.example.myfirstapp.WebtoonComment.Entities;

import com.example.myfirstapp.baseClass.ResponseBaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;


public class ResponseCommentData extends ResponseBaseData {
    @SerializedName("data")
    @Expose
    private List<CommentData> data;

    public ResponseCommentData(Integer code, String message, List<CommentData> data) {
        super(code, message);
        this.data = data;
    }

    public List<CommentData> getData() {
        return data;
    }
}
