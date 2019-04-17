package com.example.myfirstapp.Main.Entities;

import java.util.List;

import com.example.myfirstapp.baseClass.ResponseBaseData;
import com.example.myfirstapp.common.Entities.WebtoonData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseWebtoonListData extends ResponseBaseData {

    @SerializedName("result")
    @Expose
    private List<WebtoonData> result = null;

    /**
     *
     * @param message
     * @param result
     * @param code
     */
    public ResponseWebtoonListData(Integer code, String message, List<WebtoonData> result) {
        super(code, message);
        this.result = result;
    }

    public List<WebtoonData> getResult() {
        return result;
    }

    public void setResult(List<WebtoonData> result) {
        this.result = result;
    }
}