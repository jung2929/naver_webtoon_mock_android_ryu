package com.example.myfirstapp.Main.Entities;

import com.example.myfirstapp.baseClass.ResponseBaseData;
import com.example.myfirstapp.common.Entities.WebtoonData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMyWebtoonListData extends ResponseBaseData {
    @SerializedName("list")
    @Expose
    private List<WebtoonData> webtoonList = null;

    /**
     *
     * @param message
     * @param code
     */
    public ResponseMyWebtoonListData(Integer code, String message, List<WebtoonData> webtoonList) {
        super(code, message);
        this.webtoonList = webtoonList;
    }
    public List<WebtoonData> getWebtoonList() {
        return webtoonList;
    }

    public void setWebtoonList(List<WebtoonData> webtoonList) {
        this.webtoonList = webtoonList;
    }

}