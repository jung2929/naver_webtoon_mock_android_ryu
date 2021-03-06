package com.example.myfirstapp.WebtoonViewer.Entities;

import com.example.myfirstapp.baseClass.ResponseBaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseWebtoonContentViewData extends ResponseBaseData {
    @SerializedName("result")
    @Expose
    private List<WebtoonContentViewData> result = null;
    @SerializedName("check")
    @Expose
    private int check;
    @SerializedName("comment")
    @Expose
    private int comment;

    public ResponseWebtoonContentViewData(Integer code, String message, List<WebtoonContentViewData> result) {
        super(code, message);
        this.result = result;
    }

    public List<WebtoonContentViewData> getResult() {
        return result;
    }

    public int getCheck() {
        return check;
    }

    public int getComment() {
        return comment;
    }
}
