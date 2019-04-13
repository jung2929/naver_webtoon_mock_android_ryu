package com.example.myfirstapp.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMyWebtoonListData extends ResponseBaseData{

    @SerializedName("list")
    @Expose
    private List<WebtoonListData> webtoonList = null;

    /**
     *
     * @param message
     * @param code
     */
    public ResponseMyWebtoonListData(Integer code, String message, List<WebtoonListData> webtoonList) {
        super(code, message);
        this.webtoonList = webtoonList;
    }
}