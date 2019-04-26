package com.example.myfirstapp.WebtoonContentsList.Entities;

import com.example.myfirstapp.baseClass.ResponseBaseData;
import com.example.myfirstapp.common.Entities.WebtoonContentsData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseGetFirstStoryData extends ResponseBaseData {

    @SerializedName("list")
    @Expose
    private WebtoonContentsData webtoonContentsData;

    public ResponseGetFirstStoryData(Integer code, String message, WebtoonContentsData webtoonContentsData) {
        super(code, message);
        this.webtoonContentsData = webtoonContentsData;
    }

    public WebtoonContentsData getWebtoonContentsData() {
        return webtoonContentsData;
    }
}
