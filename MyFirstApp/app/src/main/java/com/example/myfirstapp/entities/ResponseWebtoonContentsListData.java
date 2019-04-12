package com.example.myfirstapp.entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseWebtoonContentsListData extends ResponseBaseData{

    @SerializedName("result")
    @Expose
    private List<WebtoonContentsData> result = null;
    /**
     *
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

}