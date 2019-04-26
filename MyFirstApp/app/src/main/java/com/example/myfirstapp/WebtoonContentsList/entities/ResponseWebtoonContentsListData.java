package com.example.myfirstapp.WebtoonContentsList.Entities;

import java.util.List;

import com.example.myfirstapp.baseClass.ResponseBaseData;
import com.example.myfirstapp.common.Entities.WebtoonContentsData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseWebtoonContentsListData extends ResponseBaseData {

    @SerializedName("result")
    @Expose
    private List<WebtoonContentsData> result = null;
    @SerializedName("check")
    @Expose
    private int check;
    /**
     * @param message
     * @param result
     * @param code
     */
    public ResponseWebtoonContentsListData(Integer code, String message, List<WebtoonContentsData> result) {
        super(code, message);
        this.result = result;
    }

    public void setResult(List<WebtoonContentsData> result) {
        this.result = result;
    }

    public List<WebtoonContentsData> getResult() {
        return result;
    }

    public int getCheck() {
        return check;
    }
}