package com.example.myfirstapp.WebtoonSearch.Entities;

import com.example.myfirstapp.baseClass.ResponseBaseData;
import com.example.myfirstapp.common.Entities.WebtoonData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseWebtoonSearchData extends ResponseBaseData {
    @SerializedName("data")
    @Expose
    List<WebtoonData> result;

    public ResponseWebtoonSearchData(Integer code, String message, List<WebtoonData> result) {
        super(code, message);
        this.result = result;
    }

    public List<WebtoonData> getResult() {
        return result;
    }
}
